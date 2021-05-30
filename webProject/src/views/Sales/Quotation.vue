<template>
	<div class="office-disk-main">
		<SalesSearchForm></SalesSearchForm>
		<div>
			<el-button v-if="!isAdmin" style="margin-bottom: 20px;" type="primary" size="medium" @click="addBtnEvent()" icon="el-icon-plus">开具报价单</el-button>
		</div>
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border>
			<el-table-column fixed prop="sqId" label="单据编号" width="200"></el-table-column>
			<el-table-column prop="state" label="销售状态" width="100"
				:filters="[{ text: '待审核', value: 0 }, { text: '已出库', value: 1 },{ text: '询价审核失败', value: -1 }]"
				:filter-method="filterType">
				<template #default="scope">
					<el-tag
					  :type="tagType(scope.row.state)"
					  disable-transitions>{{scope.row.state}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column fixed prop="agent" label="报价经手人" width="150"></el-table-column>
			<el-table-column prop="customer" label="客户" width="200"></el-table-column>
			<el-table-column prop="saleType" label="销售类型" width="150"></el-table-column>
			<el-table-column prop="invoiceType" label="发票类型" width="150"></el-table-column>
			<el-table-column prop="settlementWay" label="结算方式" width="150"></el-table-column>
			<el-table-column prop="total" label="金额" width="100"></el-table-column>
			<el-table-column prop="agentDate" label="发生日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="150">
				<template #default="scope">
					<el-button
					  @click.native.prevent="seeBtnEvent(scope.row)"
					  type="primary"
					  size="small">
					  详细
					</el-button>
					<el-button
						v-if="isAdmin || isSuperAdmin"
						v-show="scope.row.state=='待审批'?true:false"
					  @click.native.prevent="approvalBtnEvnet(scope.row)"
					  type="warning"
					  size="small">
					  审批
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<div class="contactsDialogClass">
			<el-dialog
			  title="填写销售报价单信息"
			  v-model="quotationDialogVisible"
			  width="750px"
			  :before-close="handleClose">
			  <QuotationForm ref="quotationFormData" 
					:isAdd="isAdd" 
					:propsFormData="propsFormData"></QuotationForm>
			  <template #footer>
				<span>
					<el-button @click="quotationDialogVisible = false">取 消</el-button>
					<el-button type="primary" v-show="isAdd" @click="entryApplyEvent()">确定开具</el-button>
					<el-button type="warning" v-show="isExamine" @click="entryApprovalEmevt()">确定审核</el-button>
				</span>
			  </template>
			</el-dialog>
		</div>
	</div>
</template>

<script>
let isAdmin = false;
let isSuperAdmin = false;
let tableData = [];
let quotationDialogVisible = false;
let isFristShowQuotationDialog = true;	//是否第一次显示弹框
let isExamine = false;	//当前操作是否为审批,false为询价，true为审批
let isAdd = false;	//当前操作是否为添加
let quotationDialogTitle = "填写销售报价单信息";
let currentOperationSqId = '';//当前操作的报价单编号
let propsFormData={};//向子组件传递数据
import SalesSearchForm from '@/components/Sales/SalesSearchForm.vue';
import QuotationForm from '@/components/Sales/QuotationForm.vue';
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'Quotation',
	components:{
		SalesSearchForm,QuotationForm
	},
	data(){
		return{
			isAdmin,isSuperAdmin,
			tableData,quotationDialogVisible,isFristShowQuotationDialog,isExamine,
			quotationDialogTitle,currentOperationSqId,isAdd,propsFormData
		}
	},
	methods:{
		//点击添加报价单按钮事件
		addBtnEvent(){
			this.isAdd = true;
			this.isExamine = false;
			this.quotationDialogTitle = "填写销售报价单信息";
			if(!this.isFristShowQuotationDialog){
				let propsFormDataTemp = {
					sqId: "",customer: "",saleType: "",invoiceType: "",settlementWay: "",
					agent: 0,agentDate: "",note: "",deliveryAgent: "",deliveryAgentDate: "",deliveryNote: "",
					warehouse: "",total: "",state:'',arrivalWay:''
				};
				let propsTableDataTemp = [{
					pid:'',name:'',model:'',pic:'',price:0,num:0,total:0,surplusNum:0
				}];
				this.$refs.quotationFormData.formData = propsFormDataTemp;
				this.$refs.quotationFormData.tableData = propsTableDataTemp;
			}
			this.showQuotationDialog();
		},
		//点击查看按钮事件
		seeBtnEvent(row){
			this.isAdd = false;
			this.isExamine = false;
			this.propsFormData = row;
			this.quotationDialogTitle = "销售报价单信息";
			if(!this.isFristShowQuotationDialog){
				this.$refs.quotationFormData.queryFun(this.propsFormData.sqId);	
			}
			this.showQuotationDialog();
		},
		//点击审批按钮事件
		approvalBtnEvnet(row){
			this.isAdd = false;
			this.isExamine = true;
			this.propsFormData = row;
			this.quotationDialogTitle = "审核销售报价单信息";
			if(!this.isFristShowQuotationDialog){
				this.$refs.quotationFormData.queryFun(this.propsFormData.sqId);	
			}
			this.showQuotationDialog();
		},
		showQuotationDialog(){
			if(this.isFristShowQuotationDialog){
				this.isFristShowQuotationDialog = false;
			}
			this.quotationDialogVisible = true;
		},
		//点击确定开单按钮
		entryApplyEvent(){
			let data = this.$refs.quotationFormData.formData;
			data.state = '待审批';
			data.salesItemList = this.$refs.quotationFormData.tableData;
			//将字符串转为
			console.log(data);
			this.submitQuotation(data);
		},
		//点击确定审批按钮
		entryApprovalEmevt(){
			this.$confirm('确定审核后无法变更状态，请谨慎操作，是否审核？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				console.log("点击确定审批按钮");
				let data = {
					sqId:this.propsFormData.sqId,
					state:"待发货"
				};
				console.log(data);
				this.updateQuotationState(data);
				this.quotationDialogVisible = false;
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
			if(state == '待审批') return 'warning';
			else if(state == '待发货') return '';
			else if(state == '已出库') return 'success';
			else return 'success';
		},
		filterType(value, row) {
			return row.type === value;
		},
		//更新报价单状态为
		updateQuotationState(data){
			submitDataForPost("/sales-quotation/update-state",data).then(res=>{
				//console.log("返回的数据---");
				for(let i=0;i<this.tableData.length;i++){
					if(data.sqId == this.tableData[i].sqId){
						this.tableData[i].state = data.state;
						break;
					}
				}
				this.$message({
					type: 'success',
					message: '开具询价单成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '开具询价单失败!'
				});
			});
		},
		//提交报价单数据
		submitQuotation(data){
			submitDataForPost("/sales-quotation/insert-update",data).then(res=>{
				//console.log("返回的数据---");
				this.tableData.unshift(res.data.data);
				console.log(this.tableData);
				this.quotationDialogVisible = false;
				this.$message({
					type: 'success',
					message: '开具询价单成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '开具询价单失败!'
				});
			});
		},
		//销售报价单页面打开时拉取信息
		getInitInfo(){
			let params = {};
			params.agent = null;
			console.log(params);
			GetForParams("/sales-quotation/all",params).then(res=>{
				this.tableData = res.data.data;
				console.log(this.tableData);
			}).catch(err=>{
				console.log(err);
			});
		},
		authorityJudgment(){
			//获取职位
			let pname = this.$store.getters.employee.position.jobTitle;
			if(pname == '超级管理'){
				this.isSuperAdmin = true;
				this.isAdmin = false;
			}
			else if(pname == '管理组'){
				this.isSuperAdmin = false;
				this.isAdmin = true;
			}else{
				this.isSuperAdmin = false;
				this.isAdmin = false;
			}
		}
	},
	mounted() {
		this.authorityJudgment();
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
