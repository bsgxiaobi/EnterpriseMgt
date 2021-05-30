<template>
	<div class="office-disk-main">
		<ApplySearchForm></ApplySearchForm>
		<!-- <div>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="inquiryDialogVisible = true" icon="el-icon-plus">开具询价单</el-button>
		</div> -->
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border>
			<el-table-column fixed prop="pwId" label="入库单据编号" width="150"></el-table-column>
			<el-table-column fixed prop="purchaseInquiry.poId" label="询价单据编号" width="150"></el-table-column>
			<el-table-column fixed prop="warehousingState" label="入库状态" width="100"
				:filters="[{ text: '待入库', value: '' },{ text: '待入库', value: '待入库' }, { text: '已入库', value: '已入库' }]"
				:filter-method="filterType">
				<template #default="scope">
					<el-tag
					  :type="tagType(scope.row.warehousingState)"
					  disable-transitions>{{scope.row.warehousingState == ''?'待入库':scope.row.warehousingState}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="agentId" label="经手人" width="150"></el-table-column>
			<el-table-column prop="warehouse" label="库房" width="150"></el-table-column>
			<el-table-column prop="agentDate" label="入库日期" width="200"></el-table-column>
			<el-table-column prop="purchaseInquiry.supplierId" label="供应商" width="200"></el-table-column>
			<el-table-column prop="purchaseInquiry.puchaseWay" label="采购方式" width="150"></el-table-column>
			<el-table-column prop="purchaseInquiry.invoiceType" label="发票类型" width="150"></el-table-column>
			<el-table-column prop="purchaseInquiry.settlementWay" label="结算方式" width="150"></el-table-column>
			<el-table-column prop="warehousingTotal" label="金额" width="150"></el-table-column>
			<el-table-column fixed="right" label="操作" width="150">
				<template #default="scope">
					<el-button
					  @click.native.prevent="seeEvent(scope.row)"
					  type="success"
					  size="small">
					  查看
					</el-button>
					<el-button
					  @click.native.prevent="warehousingEvent(scope.row)"
					  type="primary"
					  size="small">
					  入库
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<div class="contactsDialogClass">
			<el-dialog
			  title="填写采购入库单信息"
			  v-model="warehousingDialogVisible"
			  width="750px"
			  :before-close="handleClose">
			  <WarehousingForm ref="WarehousingFormData" :propsFormData="propsFormData" :propsPurchaseInquiry="propsPurchaseInquiry" :seeOrWarehousing="seeOrWarehousing"></WarehousingForm>
			  <template #footer>
				<span>
					<el-button @click="warehousingDialogVisible = false">取 消</el-button>
					<el-button type="primary" v-show="seeOrWarehousing" @click="entryWarehousingEvent">确定入库</el-button>
					<el-button type="primary" v-show="!seeOrWarehousing" @click="entryApprovalEvent">确定审核</el-button>
				</span>
			  </template>
			</el-dialog>
		</div>
	</div>
</template>

<script>
let tableData = [];
let warehousingDialogVisible = false;
let seeOrWarehousing = false;//查看还是入库，false 查看
let isFristDialogVisible = true;	//是否第一次显示弹框
let propsPurchaseInquiry  = {};	//向子组件传值
let propsFormData = {};//向子组件传值1
import ApplySearchForm from '@/components/Purchase/ApplySearchForm.vue';
import WarehousingForm from '@/components/Purchase/WarehousingForm.vue';
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'Warehousing',
	components:{
		ApplySearchForm,WarehousingForm
	},
	data(){
		return{
			tableData,warehousingDialogVisible,seeOrWarehousing,isFristDialogVisible,
			propsPurchaseInquiry,propsFormData
		}
	},
	methods:{
		//点击入库事件
		warehousingEvent(row){
			this.seeOrWarehousing = true;
			this.showWarehousingDialog(row);
		},
		//点击查看事件
		seeEvent(row){
			this.seeOrWarehousing = false;
			this.showWarehousingDialog(row);
		},
		//显示入库信息弹窗
		showWarehousingDialog(row){
			this.warehousingDialogVisible = true;
			if(this.isFristDialogVisible){
				this.propsPurchaseInquiry = row.purchaseInquiry;
				this.propsFormData = row;
				this.propsFormData.piId = this.propsFormData.purchaseInquiry.piId;
				this.propsFormData.poId = this.propsFormData.purchaseInquiry.poId;
				// delete this.propsFormData.purchaseInquiry;
				this.isFristDialogVisible = false;
			}
			
		},
		//点击确定入库事件
		entryWarehousingEvent(){
			this.$confirm('开单后无法修改，请谨慎操作，是否开单？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				this.addOrUpdateWarehousing();
				this.warehousingDialogVisible = false;
			  })
			  .catch(_ => {});
		},
		//点击确定审核事件
		entryApprovalEvent(){
			this.$confirm('开单后无法修改，请谨慎操作，是否开单？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				// done();
				this.warehousingDialogVisible = false;
			  })
			  .catch(_ => {});
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		tagType(state){
			if(state == '' || state === '待入库') return 'warning';
			else return 'success';
		},
		filterType(value, row) {
			return row.type === value;
		},
		showTagText(txt){
			console.log(txt);
			if(txt == '') return '待入库';
			else return txt;
		},
		//入库或者更新输入单
		addOrUpdateWarehousing(){
			console.log("入库或者更新输入单");
			let temp = this.$refs.WarehousingFormData.formData;
			console.log(temp);
			let warehousingFormData = {
				pwId: temp.pwId,
				poId: temp.poId,
				piId: temp.piId,
				agentId: this.$store.getters.employeeId,
				note: temp.note,
				warehouse: temp.warehouse,
				warehousingTotal: this.$refs.WarehousingFormData.currentWarehousingTotal,
				warehousingState: '已入库',
				purchaseItemList:this.$refs.WarehousingFormData.tableData
			};
			let purchaseInquiryTemp = temp.purchaseInquiry;
			
			// let purchaseItem = this.$refs.WarehousingFormData.tableData;
			// let data={
			// 	warehousing:warehousingFormData,
			// 	item:purchaseItem
			// }
			console.log(warehousingFormData);
			submitDataForPost("/purchase-warehousing/insert-update",warehousingFormData).then(res=>{
				console.log(res.data);
				let dataTemp = res.data.data;
				dataTemp.purchaseInquiry = purchaseInquiryTemp;
				for(let i=0;i<this.tableData.length;i++){
					if(warehousingFormData.pwId == this.tableData[i].pwId){
						console.log("匹配到了");
						this.tableData[i]= dataTemp;
						break;
					}
				}
				this.$message({
					type: 'success',
					message: '入库成功!'
				});
			}).catch(err=>{
				this.$message({
					type: 'error',
					message: '入库失败!'
				});
			});
		},
		//采购入库单页面打开时拉取信息
		getInitInfo(){
			let params = {};
			params.employeeId = null;
			console.log(params);
			GetForParams("/purchase-warehousing/all",params).then(res=>{
				this.tableData = res.data.data;
				console.log(this.tableData);
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
@import url("../../assets/css/Public/public.scss");

.contactsDialogClass .el-dialog__body{
	padding:15px 15px 0px 15px;
	height: 470px;
	overflow: auto;
}

</style>
