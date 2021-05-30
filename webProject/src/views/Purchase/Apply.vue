<template>
	<div class="office-disk-main">
		<ApplySearchForm></ApplySearchForm>
		<div>
			<el-button v-show="!isAdmin" style="margin-bottom: 20px;" type="primary" size="medium" @click="showApplyDialog()" icon="el-icon-plus">开具申请单</el-button>
		</div>
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border
			 :default-sort = "{prop: 'applyDate', order: 'descending'}">
			<el-table-column prop="poId" label="单据编号" width="150"></el-table-column>
			<el-table-column prop="employees.name" label="申请人" width="150"></el-table-column>
			<el-table-column prop="state" label="状态" width="100"
				:filters="[{ text: '待定', value: '待定' }, { text: '批准', value: '批准' },{ text: '否决', value: '否决' }]"
				:filter-method="filterType">
				<template #default="scope">
					<el-tag
					  :type="tagType(scope.row.state)"
					  disable-transitions>{{scope.row.state}}
					  <!-- {{showTagType()}} --></el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="applyDate" sortable label="申请日期" width="200"></el-table-column>
			<el-table-column prop="cause" label="申请原因"></el-table-column>
			<el-table-column prop="approver" label="审核人" width="150"></el-table-column>
			<el-table-column prop="approvalDate" sortable label="审核日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="100">
				<template #default="scope">
					<el-button
					  @click.native.prevent="showPurchaseApplyInfo(scope.row)"
					  type="success"
					  size="small">
					  查看
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<div class="contactsDialogClass">
			<el-dialog
			  :title="applyDialogTitle"
			  v-model="applyDialogVisible"
			  width="750px"
			  :before-close="handleClose">
			  <ApplyForm ref="ApplyFormData" :isApplyOperation="isApplyOperation" :selectedApplyData="selectedApplyData"></ApplyForm>
			  <template #footer>
				<span>
					<el-button @click="applyDialogVisible = false">取 消</el-button>
					<el-button type="primary"
						v-if="isApplyOperation"
					 v-show="!isAdmin" @click="entryApply">确定申请</el-button>
					<el-button type="primary" v-show="isAdmin || isSuperAdmin" @click="confirmAudit">确认审核</el-button>
				</span>
			  </template>
			</el-dialog>
		</div>
	</div>
</template>

<script>
// let tableData = [{
// 	poId:'',
// 	employeeId:'',
// 	name:'',
// 	state:0,
// 	cause:'',
// 	applyDate: '',
// 	approver:'',
// 	approvalDate: '',
// },
// ];
let isAdmin = false;
let isSuperAdmin = false;
let tableData = [];
let selectedApplyData={};
let isFristShowApplyDialogVisible = true;	//是否第一次显示申请单框
let applyDialogVisible = false;	//控制申请框显示
let applyDialogTitle = '';	//申请单框标题
let isApplyOperation = true;	//是否为开具申请单操作
let currentShowPoId = -1;	//当前显示的申请单序号
import ApplySearchForm from '@/components/Purchase/ApplySearchForm.vue';
import ApplyForm from '@/components/Purchase/ApplyForm.vue';

import {getDataForNullParams,submitDataForPost,submitDataForPostForm} from '@/network/RequestApi.js';
export default{
	name:'Company',
	components:{
		ApplySearchForm,ApplyForm
	},
	data(){
		return{
			isAdmin,isSuperAdmin,
			tableData,applyDialogVisible,
			selectedApplyData,isFristShowApplyDialogVisible,isApplyOperation,
			applyDialogTitle,currentShowPoId
		}
	},
	methods:{
		//点击查看申请单按钮
		showPurchaseApplyInfo(row){
			console.log(row);
			this.applyDialogTitle = '采购申请单信息';
			this.isApplyOperation = false;
			this.applyDialogVisible = true;
			this.currentShowPoId = row.poId;
			//第一次显示时
			if(this.isFristShowApplyDialogVisible){
				this.selectedApplyData = row;
				this.isFristShowApplyDialogVisible = false;
				return;
			}
			this.$refs.ApplyFormData.queryFun(row);
		},
		//点击开局申请单按钮
		showApplyDialog(){
			this.applyDialogTitle = '填写采购申请单信息';
			this.isApplyOperation = true;
			this.applyDialogVisible = true;
			//不是第一次显示时才重置值
			if(!this.isFristShowApplyDialogVisible){
				this.$refs.ApplyFormData.formData = this.$refs.ApplyFormData.formDataTemp;
				this.$refs.ApplyFormData.tableData.splice(0,this.$refs.ApplyFormData.tableData.length);
				this.$refs.ApplyFormData.tableData[0] = this.$refs.ApplyFormData.tebleDataTemp;
			}
			this.isFristShowApplyDialogVisible = false;
		},
		//点击确定审核按钮
		confirmAudit(){
			this.$confirm('审核后无法修改，是否审核')
			  .then(_ => {
				this.updateApplyExamine();
				this.applyDialogVisible = false;
			  })
			  .catch(_ => {});
		},
		//点击确定申请按钮
		entryApply(){
			this.$confirm('申请后后无法修改，是否申请')
			  .then(_ => {
				this.sublmitApplyData();
				this.applyDialogVisible = false;
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
		// showTagType(state){
		// 	if(state === 0) return '待定';
		// 	else return state=== 1?'批准':'否决';
		// },
		tagType(state){
			if(state === '待定') return '';
			else return state=== '批准'?'success':'danger';
		},
		//状态过滤器
		filterType(value, row) {
			return row.state === value;
		},
		//变更审核
		updateApplyExamine(){
			console.log("======准备提交的审核数据=======");
			let updateData = {
				state:null,
				poId:null,
				approver:null
			};
			updateData.state = this.$refs.ApplyFormData.formData.state;
			updateData.poId = this.$refs.ApplyFormData.formData.poId;
			updateData.approver = this.$store.getters.employeeId;
			console.log(updateData);
			submitDataForPost("/purchase-apply/update-state",updateData).then(res=>{
				console.log("成功返回数据");
				console.log(res.data);
				//设置表单的数据
				for(let i = 0;i<this.tableData.length;i++){
					if(this.tableData[i].poId == this.currentShowPoId){
						this.tableData[i].state = updateData.state;
						this.tableData[i].approver = this.$store.getters.employeeId;
						this.tableData[i].approvalDate = res.data.data;
						break;
					}
				}
				this.$message({
					type: 'success',
					message: '审核成功!'
				});
			}).catch(err=>{
				console.log("失败");
				this.$message({
					type: 'error',
					message: '审核失败!'
				});
			});
		},
		//新增申请单数据
		sublmitApplyData(){
			console.log("======准备提交的数据=======");
			// console.log(this.$refs.ApplyFormData.formData);
			// console.log(this.$refs.ApplyFormData.tableData);
			let formData = this.$refs.ApplyFormData.formData;
			//formData.employees = null;
			formData.itemList = this.$refs.ApplyFormData.tableData;
			// console.log(formData);
			submitDataForPost("/purchase-apply/add",formData).then(res=>{
				// console.log("成功返回数据");
				// console.log(res.data);
				this.$message({
					type: 'success',
					message: '开具申请单成功!'
				});
				this.getApplyList();
			}).catch(err=>{
				console.log("失败");
				this.$message({
					type: 'error',
					message: '开具申请单失败!'
				});
			});
		},
		//get请求查询采购申请单所有信息
		async getApplyList(){
			getDataForNullParams("/purchase-apply/all-purchase-apply").then(res=>{
				this.tableData = res.data.data;
				// console.log(this.tableData);
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
	mounted(){
		this.authorityJudgment();
		//*****一般把请求网络操作放在这里*****
		this.getApplyList();
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
