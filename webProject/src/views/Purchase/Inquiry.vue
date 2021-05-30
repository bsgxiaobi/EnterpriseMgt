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
			<el-table-column fixed prop="piId" label="单据编号" width="200"></el-table-column>
			<el-table-column fixed prop="inquiryState" label="询价状态" width="100"
				:filters="[{ text: '未询价', value: '' }, { text: '待审核', value: '待审核' },{ text: '审核通过', value: '审核通过' },{ text: '审核驳回', value: '审核驳回' }]"
				:filter-method="filterType">
				<template #default="scope">
					<el-tag
					  :type="tagType(scope.row.inquiryState)"
					  disable-transitions>{{scope.row.inquiryState == ''?'未询价':scope.row.inquiryState}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="purchaseApply.poId" label="采购申请单号" width="200"></el-table-column>
			<el-table-column prop="purchaseApply.approvalDate" label="申请批准时间" width="200"></el-table-column>
			<el-table-column prop="agentId" label="经手人" width="150"></el-table-column>
			<el-table-column prop="supplierId" label="供应商" width="200"></el-table-column>
			<el-table-column prop="puchaseWay" label="采购方式" width="150"></el-table-column>
			<el-table-column prop="invoiceType" label="发票类型" width="150"></el-table-column>
			<el-table-column prop="settlementWay" label="结算方式" width="150"></el-table-column>
			<el-table-column prop="inquiryTotal" label="总金额" width="150"></el-table-column>
			<el-table-column prop="agentDate" label="经办日期" width="200"></el-table-column>
			<el-table-column prop="approval" label="审核人" width="150"></el-table-column>
			<el-table-column prop="approvalDate" label="审核日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="170">
				<template #default="scope">
					<el-button
					  @click.native.prevent="showInquiryDialogByInquiryEvent(scope.row)"
					  type="primary"
					  size="small">
					  询价
					</el-button>
					<el-button
					  @click.native.prevent="showInquiryDialogByApprovalEvent(scope.row)"
					  type="warning"
					  size="small">
					  审批
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<div class="contactsDialogClass">
			<el-dialog
			  :title="inquiryDialogTitle"
			  v-model="inquiryDialogVisible"
			  width="750px"
			  :before-close="handleClose">
			  <InquiryForm ref="inquiryFormData" :isExamine="isExamine" :propsInquiryFormData="propsInquiryFormData"></InquiryForm>
			  <template #footer>
				<span>
					<el-button @click="inquiryDialogVisible = false">取 消</el-button>
					<el-button type="primary" v-show="!isExamine" @click="submitInquiryEvent()">提交询价</el-button>
					<el-button type="warning" v-show="isExamine" @click="submitInquiryStateEvent()">确定审批</el-button>
				</span>
			  </template>
			</el-dialog>
		</div>
	</div>
</template>

<script>
let tableData = [
];
let propsInquiryFormData = {};	//向InquiryForm传递的数据
let inquiryDialogVisible = false;//绑定询价信息框
let jurisdictionIsExamine = false;	//是否具备审批权
let isExamine = false;	//当前操作是否为审批,false为询价，true为审批
let isFristShowInquiryDialogVisible = true;	//是否第一次显示弹框
let currentShowInquiryDialogPoId = '';	//当前显示关联的申请单id
let inquiryDialogTitle = "填写采购询价单信息";
import ApplySearchForm from '@/components/Purchase/ApplySearchForm.vue';
import InquiryForm from '@/components/Purchase/InquiryForm.vue';

import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'Inquiry',
	components:{
		ApplySearchForm,InquiryForm
	},
	data(){
		return{
			tableData,inquiryDialogVisible,jurisdictionIsExamine,
			isFristShowInquiryDialogVisible,propsInquiryFormData,isExamine,
			currentShowInquiryDialogPoId,inquiryDialogTitle
		}
	},
	methods:{
		//点击审批事件
		showInquiryDialogByApprovalEvent(row){
			this.inquiryDialogTitle = "采购询价单信息";
			this.isExamine = true;
			this.showInquiryDialog(row);
		},
		//点击询价按钮事件
		showInquiryDialogByInquiryEvent(row){
			this.inquiryDialogTitle = "填写采购询价单信息";
			this.isExamine = false;
			this.showInquiryDialog(row);
		},
		//显示询价单信息框
		showInquiryDialog(row){
			console.log(this.tableData);
			this.currentShowInquiryDialogPoId = row.purchaseApply.poId;
			console.log("row---------------");
			console.log(row);
			// console.log("index::" + index);
			this.inquiryDialogVisible = true;
			if(this.isFristShowInquiryDialogVisible){
				console.log("this.isFristShowInquiryDialogVisible = true");
				//this.propsInquiryFormData = this.tableData[index];
				this.propsInquiryFormData = row;
				this.isFristShowInquiryDialogVisible = false;
				return;
			}
			// let data = this.tableData[index];
			let data = row;
			this.$refs.inquiryFormData.queryFun(data);
		},
		//点击确定审批事件
		submitInquiryStateEvent(){
			this.$confirm('请谨慎操作，是否更改审批状态？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				this.updateInquiryState();
				this.inquiryDialogVisible = false;
			  })
			  .catch(_ => {});
		},
		//点击提交询价事件
		submitInquiryEvent(){
			this.$confirm('请谨慎操作，是否开单？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				this.submitInquiryFormData();
				this.inquiryDialogVisible = false;
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
		tagType(inquiryState){
			if(inquiryState == '未询价') return '';
			else if(inquiryState == '待审核') return 'warning';
			else if(inquiryState == '审核通过') return 'success';
			else return 'danger';
		},
		filterType(value, row) {
			return row.inquiryState === value;
		},
		//变更审批状态
		updateInquiryState(){
			//获取新状态
			let data = {};
			data.approval = this.$store.getters.employeeId;
			data.inquiryState = this.$refs.inquiryFormData.formData.inquiryState;
			data.piId = this.$refs.inquiryFormData.formData.piId;
			console.log(data);
			submitDataForPost("/purchase-inquiry/update-state",data).then(res=>{
				console.log(res.data);
				for(let i=0;i<this.tableData.length;i++){
					if(this.currentShowInquiryDialogPoId == this.tableData[i].purchaseApply.poId){
						this.tableData[i].approvalDate = res.data.data;
						this.tableData[i].approval = data.approval;
						this.tableData[i].inquiryState = data.inquiryState;
						break;
					}
				}
				this.$message({
					type: 'success',
					message: '变更审批状态成功!'
				});
			}).catch(err=>{
				this.$message({
					type: 'error',
					message: '变更审批状态失败!'
				});
			});
		},
		//提交询价数据
		submitInquiryFormData(){
			let data = this.$refs.inquiryFormData.formData;
			data.purchaseApply = {itemList:null};
			data.purchaseApply.itemList = this.$refs.inquiryFormData.tableData;
			data.agentId = this.$store.getters.employeeId;
			data.inquiryTotal = this.$refs.inquiryFormData.currentInquiryTotal;
			data.inquiryState = '待审核';
			delete data.agentDate;
			delete data.approvalDate;
			console.log("提交询价数据");
			console.log(data);
			submitDataForPost("/purchase-inquiry/insert-update",data).then(res=>{
				console.log("返回的数据---");
				let result = res.data.data;
				// console.log(result);
				// console.log("开始比对poId");
				for(let i=0;i<this.tableData.length;i++){
					if(this.currentShowInquiryDialogPoId == this.tableData[i].purchaseApply.poId){
						// console.log("匹配到poId,所在下标：" + i);
						// console.log(this.tableData[i]);
						// console.log(this.tableData);
						result.purchaseApply.approvalDate = this.tableData[i].purchaseApply.approvalDate;
						result.purchaseApply.poId = result.poId;
						this.tableData.splice(i,1,result);
						// console.log(this.tableData);
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
		//采购询价单页面打开时拉取信息
		getInitInfo(){
			let params = {};
			params.employeeId = null;
			console.log(params);
			GetForParams("/purchase-inquiry/all-purchase-inquiry",params).then(res=>{
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
