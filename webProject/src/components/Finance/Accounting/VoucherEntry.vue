<template>
	<div class="office-disk-main">
		<!-- <SearchForm></SearchForm> -->
		<div style="margin-bottom: 10px;">
			<el-button type="primary" size="medium" @click="addApplyBtnEvent" icon="el-icon-plus">录入凭证</el-button>
		</div>
		<el-table
			:data="tableData"
			style="width: 100%"
			height="350"
			stripe
			border
			highlight-current-row
			:header-cell-style="{'text-align':'center'}"
			@row-click="tableRowClickEvent"
			:cell-style="tableRowClass">
			<el-table-column prop="bvId" label="凭证号" width="100"></el-table-column>
			<el-table-column prop="voucherWord" label="凭证字" width="100"></el-table-column>
			<el-table-column prop="state" label="状态" width="100"></el-table-column>
			<el-table-column prop="bvAbstracts" label="凭证摘要" width="300"></el-table-column>
			<el-table-column prop="total" label="发生额"></el-table-column>
			<el-table-column prop="createDate" label="制单日期" width="200"></el-table-column>
			<el-table-column prop="createName" label="制单人" width="150"></el-table-column>
			<el-table-column prop="reviewDate" label="审核日期" width="200"></el-table-column>
			<el-table-column prop="reviewName" label="审核人" width="150"></el-table-column>
			<el-table-column prop="bookkeepDate" label="记账日期" width="200"></el-table-column>
			<el-table-column prop="bookkeepName" label="记账人" width="150"></el-table-column>
			<el-table-column prop="total" label="发生额" width="100"></el-table-column>
		</el-table>
		
		<el-divider content-position="left">凭证明细:</el-divider>
		
		<el-table id="contacts-table"
			:data="sonTableData"
			stripe
			border
			:header-cell-style="{'text-align':'center'}"
			:cell-style="tableRowClass"
			height="250">
			<!-- <el-table-column fixed prop="viId" label="序号" width="300"></el-table-column> -->
			<el-table-column prop="abstracts" label="摘要" width="200"></el-table-column>
			<el-table-column prop="ledger" label="总账科目" width="300"></el-table-column>
			<el-table-column prop="detailed" label="明细科目" width="300"></el-table-column>
			<el-table-column prop="borrow" label="借方" width="100"></el-table-column>
			<el-table-column prop="credit" label="贷方" width="100"></el-table-column>
		</el-table>
		
		<!-- 记账凭证信息 -->
		<div id="voucher-dialog-view">
			<el-dialog
			  v-model="dialogVisible"
			  width="900px"
			  :before-close="handleClose">
				<VoucherForm ref="propFormData"></VoucherForm>
			  <template #footer>
				<span>
			      <el-button @click="dialogVisible = false">放弃</el-button>
				  <el-button type="primary" @click="entryApplyBtnEvent()">保存</el-button>
			    </span>
			  </template>
			</el-dialog>
		</div>
	</div>
</template>

<script>
let tableData = [];
let sonTableData = [];
let dialogVisible = false;
let isFristShowDialog = true;
let currentOperationId = null;//当前操作的Id
import VoucherForm from '@/components/Finance/Accounting/VoucherForm.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'VoucherEntry',
	components:{
		VoucherForm
	},
	data(){
		return{
			tableData,sonTableData,currentOperationId,
			dialogVisible,isFristShowDialog
		}
	},
	methods:{
		tableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		//应付(收)行点击事件
		tableRowClickEvent(row){
			this.currentOperationId = row.bvId;
			this.getAllSonInfo(row.bvId);
		},
		//点击新增应付(收)按钮事件
		addApplyBtnEvent(){
			if(this.isFristShowDialog) this.isFristShowDialog = false;
			else this.$refs.propFormData.resetData();
			this.dialogVisible = true;
		},
		//点击确定新增应付(收)单按钮
		entryApplyBtnEvent(){
			//先检查信息
			if(!this.reviewDialogInfo()) return;
			this.$confirm('单据保存后将无法修改，确定输入无误','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '再次核查',
				type: 'warning',
			}).then(_ => {
				let data = this.$refs.propFormData.formData;
				let dataItem = this.$refs.propFormData.voucherItem;
				data.createId = this.$store.getters.employeeId;
				data.createName = 'createName';
				console.log(dataItem);
				for(let i = dataItem.length-1;i>=0;i--){
					if(dataItem[i].ledger == '') dataItem.splice(i,1);
				}
				data.voucherItemList = dataItem;
				data.state = "待审核";
				console.log(data);
				// console.log(dataItem);
				this.submitRecordData(data);
			}).catch(_ => {});
		},
		//核算信息是否正确
		reviewDialogInfo(){
			//检查是否借贷平衡
			let data = this.$refs.propFormData.voucherItem;
			let borrowSum = 0;
			let creditSum = 0;
			for(let i = 0;i<data.length;i++){
				if(data[i].borrow != null) borrowSum += Number(data[i].borrow);
				if(data[i].credit != null) creditSum += Number(data[i].credit);
			}
			if(borrowSum== 0 && creditSum==0){
				this.$message({
					type: 'error',
					message: '不得提交空白凭证'
				});
				return false;
			}
			if(borrowSum != creditSum){
				// this.$alert('借贷不平衡，请核对', '提示', {
				//     confirmButtonText: '确定',
				//     type: 'error',
				//     callback: action => {}
				// });
				this.$message({
					type: 'error',
					message: '借贷不平衡，请核对'
				});
				return false;
			}
			return true;
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		showTagType(type){
			return type === 0?'批准':'否决';
		},
		tagType(type){
			return type === 0?'success':'warning';
		},
		filterType(value, row) {
			return row.type === value;
		},
		
		//提交已付(收)数据
		submitSonRecordData(data){
			delete data.createDate;
			submitDataForPost("/single-payment/insert",data).then(res=>{
				let result = res.data.data;
				console.log(result);
				this.sonTableData.unshift(res.data.data);
				this.sonDialogVisible = false;
				//修改应付(收)单中的已付(收)金额
				for(let i=0;i<this.tableData.length;i++){
					if(this.tableData[i].sapId == data.sapId){
						this.tableData[i].paid = Number(this.tableData[i].paid) + Number(data.total);
						break;
					}
				}
				this.$message({
					type: 'success',
					message: '新增已付(收)成功！'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '已付(收)信息操作失败!'
				});
			});
		},
		//提交应付(收)单数据
		submitRecordData(data){
			submitDataForPost("/voucher/insert",data).then(res=>{
				let result = res.data.data;
				this.tableData.unshift(res.data.data);
				this.dialogVisible = false;
				this.$message({
					type: 'success',
					message: '新增凭证'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '新增凭证操作失败!'
				});
			});
		},
		//拉取应付(收)单的所有已付(收)信息
		async getAllSonInfo(id){
			let params = {};
			params.id = id;
			GetForParams("/voucher-item/all",params).then(res=>{
				this.sonTableData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//页面打开时拉取所有信息
		async getInitInfo(){
			getDataForNullParams("/voucher/all").then(res=>{
				this.tableData = res.data.data;
				// console.log(res.data.data);
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		this.getInitInfo();
	}
}
</script>

<style lang="scss">
// @import url("../../assets/css/Public/public.scss");

#voucher-dialog-view .el-dialog__body{
	padding:0px 15px;
	// height: 400px;
	overflow: auto;
}
#voucher-dialog-view .el-dialog__header{
	padding-bottom: 0px;
}
#voucher-dialog-view .el-dialog__footer{
	padding-top: 0px;
}
</style>
