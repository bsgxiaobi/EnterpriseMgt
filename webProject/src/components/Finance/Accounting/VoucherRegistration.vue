<template>
	<div class="office-disk-main">
		<!-- <SearchForm></SearchForm> -->
		<div style="margin-bottom: 10px;">
			<el-button type="primary" size="medium" @click="addApplyBtnEvent" icon="el-icon-plus">批量登账</el-button>
			<el-button type="warning" size="medium" @click="addApplyFailedBtnEvent" icon="el-icon-plus">批量退回</el-button>
		</div>
		<el-table
			:data="tableData"
			style="width: 100%"
			height="350"
			stripe
			border
			ref="multipleTable"
			highlight-current-row
			:header-cell-style="{'text-align':'center'}"
			@row-click="tableRowClickEvent"
			:cell-style="tableRowClass"
			@selection-change="handleSelectionChange">
			<el-table-column fixed="left" :selectable="tableColumnCheckSelect"
			      type="selection"
				  header-align="center">
			</el-table-column>
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
let currentSelectedItem = [];
import VoucherForm from '@/components/Finance/Accounting/VoucherForm.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'VoucherVerification',
	components:{
		VoucherForm
	},
	data(){
		return{
			tableData,sonTableData,currentOperationId,
			dialogVisible,isFristShowDialog,currentSelectedItem
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
		clearTableSelection(){
			this.$refs.multipleTable.clearSelection();
		},
		//表格选中改变时
		handleSelectionChange(val){
			// console.log(val);
			this.currentSelectedItem = val;
		},
		//选择按钮是否可以选中
		tableColumnCheckSelect(row,index){
			if(row.state == '待登账') return true;
			else return false;
		},
		//应付(收)行点击事件
		tableRowClickEvent(row){
			this.currentOperationId = row.bvId;
			this.getAllSonInfo(row.bvId);
		},
		addApplyFailedBtnEvent(){
			if(this.currentSelectedItem.length == 0){
				this.$message({
					type: 'error',
					message: '请先选择凭证！'
				});
				return;
			}
			this.$confirm('一旦提交无法修改，是否继续审核凭证?','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '重新选择',
				type: 'warning',
			}).then(_ => {
				let data = [];
				for(let i=0;i<this.currentSelectedItem.length;i++){
					let temp = {};
					temp.bvId = this.currentSelectedItem[i].bvId;
					temp.bookkeepId = this.$store.getters.employeeId;
					temp.bookkeepName = 'bookkeepName';
					temp.state = '登账退回';
					data.push(temp);
				}
				this.submitRecordData(data);
			}).catch(_ => {});
		},
		//点击新增应付(收)按钮事件
		addApplyBtnEvent(){
			if(this.currentSelectedItem.length == 0){
				this.$message({
					type: 'error',
					message: '请先选择凭证！'
				});
				return;
			}
			this.$confirm('一旦提交无法修改，是否继续退回凭证?','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '重新选择',
				type: 'warning',
			}).then(_ => {
				let data = [];
				for(let i=0;i<this.currentSelectedItem.length;i++){
					let temp = {};
					temp.bvId = this.currentSelectedItem[i].bvId;
					temp.bookkeepId = this.$store.getters.employeeId;
					temp.bookkeepName = 'bookkeepName';
					temp.state = '已登账';
					data.push(temp);
				}
				this.submitRecordData(data);
				
			}).catch(_ => {});
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
		//提交应付(收)单数据
		submitRecordData(data){
			submitDataForPost("/voucher/bookkeeping",data).then(res=>{
				this.clearTableSelection();
				for(let i=0;i<data.length;i++){
					for(let j=0;j<this.tableData.length;j++){
						if(this.tableData[j].bvId == data[i].bvId){
							this.tableData[j].bookkeepId = data[i].bookkeepId;
							this.tableData[j].bookkeepName = data[i].bookkeepName;
							this.tableData[j].state = data[i].state;
							this.tableData[j].bookkeepDate = res.data.data;
							break;
						}
					}
				}
				this.$message({
					type: 'success',
					message: '凭证登账处理成功'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '处理失败!'
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
			getDataForNullParams("/voucher/all-bookkeeping").then(res=>{
				this.tableData = res.data.data;
				// console.log(res.data.data);
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		this.getInitInfo();
	},
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
