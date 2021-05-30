<template>
	<div class="office-disk-main">
		
		<div>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="applyBtnEvent" icon="el-icon-plus">申请支领</el-button>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="grantBtnEvent" icon="el-icon-plus">物品发放</el-button>
		</div>
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border>
			<el-table-column fixed prop="claimId" label="单据编号" width="150"></el-table-column>
			<el-table-column prop="applyState" label="申请状态" width="150"
				:filters="[{ text: '待批准', value: '待批准' }, { text: '审核未通过', value: '审核未通过' },{ text: '待领取', value: '待领取' },{ text: '完成', value: '完成' }]"
				:filter-method="filterApplyState">
				<template #default="scope">
					<el-tag
					  :type="tagApplyStateType(scope.row.applyState)"
					  disable-transitions>{{scope.row.applyState}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="claimType" label="支领类型" width="150"
				:filters="[{ text: '申请', value: '申请' }, { text: '发放', value: '发放' }]"
				:filter-method="filterClaimType">
				<template #default="scope">
					<el-tag
					  :type="tagClaimTypeType(scope.row.claimType)"
					  disable-transitions>{{scope.row.claimType}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column fixed prop="warehouse" label="仓库" width="150"></el-table-column>
			<el-table-column prop="applyId" label="申请人" width="100"></el-table-column>
			<el-table-column prop="agentId" label="经手人" width="100"></el-table-column>
			<el-table-column prop="claimTotal" label="总价" width="100"></el-table-column>
			<el-table-column prop="receiveDate" label="领取日期" width="200"></el-table-column>
			<el-table-column prop="agentDate" label="经手日期" width="200"></el-table-column>
			<el-table-column prop="applyDate" label="申请日期" width="200"></el-table-column>
			<el-table-column prop="cause" label="原因" width="300"></el-table-column>
			<el-table-column fixed="right" label="操作" width="150">
				<template #default="scope">
					<el-button
					  @click.native.prevent="seeBtnEvent(scope.row)"
					  type="success"
					  size="small">
					  查看
					</el-button>
					<el-button
					  @click.native.prevent="approvalBtnEvent(scope.row)"
					  v-show="isShowApproval(scope.row.applyState)"
					  type="warning"
					  size="small">
					  审批
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<div class="contactsDialogClass">
			<el-dialog
			  title="填写支领申请单信息"
			  v-model="claimDialogVisible"
			  width="750px"
			  :before-close="handleClose">
			  <ClaimForm ref="claimFormData" :transmitId="currentOperationId"
				:isAdd="isAdd" :isExamine="isExamine" :isApply="isApply"></ClaimForm>
			  <template #footer>
				<span>
					<el-button @click="claimDialogVisible = false">取 消</el-button>
					<el-button v-show="isApply" type="success" @click="entryApplyEvent()">确定申请</el-button>
					<el-button v-show="isGrant" type="primary" @click="entryGrantEvent()">确定发放</el-button>
					<el-button v-show="isExamine" type="warning" @click="entryApprovalEvent()">确定审核</el-button>
				</span>
			  </template>
			</el-dialog>
		</div>

	</div>
</template>

<script>
let tableData = [];
let claimDialogVisible = false;
let isFristShowDialog = true;	//是否第一次显示弹框
let isExamine = false;	//当前操作是否为审批,false为询价，true为审批
let isAdd = false;	//当前操作是否为添加
let isApply = false;//是否为申请操作
let claimDialogTitle = "填写支领申请单信息";
let currentOperationId = '';//当前操作的单号
let propsFormData={};//向子组件传递数据
import ClaimForm from '@/components/Warehouse/ClaimForm.vue';
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'Claim',
	components:{
		ClaimForm
	},
	computed:{
		isShowApproval(){
			return function(state){
				if(state == '待审批') return true;
				else return false;
			}
		},
		isGrant(){
			if(this.isAdd && !this.isApply)return true;
			else return false;
		}
	},
	data(){
		return{
			tableData,claimDialogVisible,isFristShowDialog,isExamine,isAdd,claimDialogTitle,
			currentOperationId,propsFormData,isApply
		}
	},
	methods:{
		//点击申请支领按钮事件
		applyBtnEvent(){
			this.isApply = true;
			this.isAdd = true;
			this.isExamine = false;
			this.claimDialogTitle = '填写支领申请单信息';
			if(!this.isFristShowDialog){
				//清空单据填写页面的信息
				this.$refs.claimFormData.resetInfo();
			}
			this.showEjectDialog();
		},
		//点击物品发放事件
		grantBtnEvent(){
			this.isApply = false;
			this.isAdd = true;
			this.isExamine = false;
			this.claimDialogTitle = '填写支领发放单信息';
			if(!this.isFristShowDialog){
				//清空单据填写页面的信息
				this.$refs.claimFormData.resetInfo();
			}
			this.showEjectDialog();
		},
		//点击查看按钮事件
		seeBtnEvent(row){
			this.isApply = false;
			this.isAdd = false;
			this.isExamine = false;
			this.currentOperationId = row.claimId;
			this.quotationDialogTitle = "查看支领发放单信息";
			if(!this.isFristShowDialog){
				this.$refs.claimFormData.queryFun(this.currentOperationId);	
			}
			this.showEjectDialog();
		},
		//点击审核按钮事件
		approvalBtnEvent(row){
			this.isApply = false;
			this.isAdd = false;
			this.isExamine = true;
			this.currentOperationId = row.claimId;
			this.quotationDialogTitle = "审核支领发放单信息";
			if(!this.isFristShowDialog){
				this.$refs.claimFormData.queryFun(this.currentOperationId);	
			}
			this.showEjectDialog();
		},
		//显示物品弹窗
		showEjectDialog(){
			if(this.isFristShowDialog){
				this.isFristShowDialog = false;
			}
			this.claimDialogVisible = true;
		},
		//点击确定申请按钮
		entryApplyEvent(){
			this.$confirm('一旦开单后无法修改，请谨慎操作，是否继续申请？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				let data = this.$refs.claimFormData.formData;
				data.applyState = '待审批';
				data.claimType = '申请';
				data.applyId = this.$store.getters.employeeId;
				data.claimTotal = this.$refs.claimFormData.currentClaimTotal;
				data.claimItemList = this.$refs.claimFormData.tableData;
				console.log(data);
				this.submitClaim(data);
			  })
			.catch(_ => {});
		},
		//点击确定发放按钮
		entryGrantEvent(){
			this.$confirm('一旦发放后无法修改，请谨慎操作，是否继续发放？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				let data = this.$refs.claimFormData.formData;
				data.claimType = '发放';
				data.agentId = this.$store.getters.employeeId;
				data.claimItemList = this.$refs.claimFormData.tableData;
				data.claimTotal = this.$refs.claimFormData.currentClaimTotal;
				console.log(data);
				this.submitClaim(data);
			  })
			.catch(_ => {});
		},
		//点击确定审核按钮
		entryApprovalEvent(){
			this.$confirm('一旦审核后无法修改，请谨慎操作，是否继续审核？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				let data = this.$refs.claimFormData.formData;
				if(this.$refs.claimFormData.currentApplyState){
					data.applyState = '待领取';
				}else{
					data.applyState = '审核未通过';
				}
				data.agentId = this.$store.getters.employeeId;
				data.claimItemList = null;
				delete data.applyDate;
				delete data.agentDate;
				console.log(data);
				this.submitClaimApproval(data);
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
		tagApplyStateType(applyState){
			if(applyState == '待批准') return 'primary';
			else if(applyState == '审核未通过') return 'danger';
			else if(applyState == '待领取') return 'warning';
			else return 'success';
		},
		tagClaimTypeType(claimType){
			return claimType == '申请'?'success':'primary';
		},
		filterApplyState(value, row) {
			return row.type === value;
		},
		filterClaimType(value, row) {
			return row.type === value;
		},
		//提交支领发放单数据
		submitClaim(data){
			submitDataForPost("/inventory-claim/insert-update",data).then(res=>{
				//console.log("返回的数据---");
				this.tableData.unshift(res.data.data);
				console.log(this.tableData);
				this.claimDialogVisible = false;
				this.$message({
					type: 'success',
					message: '开具支领发放单成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '开具支领发放单失败!'
				});
			});
		},
		//审核支领发放单
		submitClaimApproval(data){
			submitDataForPost("/inventory-claim/approval",data).then(res=>{
				for(let i=0;i<this.tableData.length;i++){
					if(data.claimId == this.tableData[i].claimId){
						this.tableData[i].agentId = data.agentId;
						this.tableData[i].applyState = data.applyState;
						this.tableData[i].agentDate = res.data.data;
						break;
					}
				}
				this.claimDialogVisible = false;
				this.$message({
					type: 'success',
					message: '审核成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '审核失败!'
				});
			});
		},
		//支领发放单页面打开时拉取信息
		async getInitInfo(){
			let params = {};
			params.agent = null;
			// console.log(params);
			GetForParams("/inventory-claim/all",params).then(res=>{
				this.tableData = res.data.data;
				// console.log(this.tableData);
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
