<template>
	<div class="office-disk-main">
		<SalesSearchForm></SalesSearchForm>
		<div>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="addBtnEvent()" icon="el-icon-plus">开具售后单</el-button>
		</div>
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border>
			<el-table-column prop="customer" label="申请客户" width="200"></el-table-column>
			<el-table-column fixed prop="sqId" label="关联订单" width="200"></el-table-column>
			<el-table-column prop="applyDate" label="请求日期" width="200"></el-table-column>
			<el-table-column prop="priority" label="级别" width="100"></el-table-column>
			<el-table-column prop="state" label="处理状态" width="100"></el-table-column>
			<el-table-column prop="agent" label="跟进人" width="100"></el-table-column>
			<el-table-column prop="abstracts" label="请求摘要" width="300"></el-table-column>
			<el-table-column prop="isVisit" label="是否回访" width="100"
				:filters="[{ text: '是', value: 1 }, { text: '否', value: 0 }]"
				:filter-method="filterType">
				<template #default="scope">
					<el-tag
					  :type="tagType(scope.row.isVisit)"
					  disable-transitions>{{showTagType(scope.row.isVisit)}}</el-tag>
				</template></el-table-column>
			<el-table-column prop="agentDate" label="制单日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="150">
				<template #default="scope">
					<el-button
					  @click.native.prevent="seeBtnEvent(scope.row)"
					  type="primary"
					  size="small">
					  详情
					</el-button>
					<el-button
					  @click.native.prevent="modifyBtnEvnet(scope.row)"
					  type="warning"
					  size="small">
					  修改
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<div class="contactsDialogClass">
			<el-dialog
			  title="填写销售售后单信息"
			  v-model="afterDialogVisible"
			  width="750px"
			  :before-close="handleClose">
			  <AfterForm ref="afterFormData" :isAdd="isAdd" :isModify="isModify"
				:propsFormDataSaId="propsFormDataSaId"></AfterForm>
			  <template #footer>
				<span>
					<el-button @click="afterDialogVisible = false">取 消</el-button>
					<el-button type="primary" @click="entryAddOrModifyEvent()">{{entryBtnTitle}}</el-button>
				  
				</span>
			  </template>
			</el-dialog>
		</div>
	</div>
</template>

<script>

let afterDialogVisible = false;
let tableData = [];
let isFristShowAfterDialog = true;	//是否第一次显示弹框
let afterDialogTitle = "";
let isModify = false;
let isAdd = false;
let propsFormDataSaId = -1;
let entryBtnTitle = '确定添加';
import SalesSearchForm from '@/components/Sales/SalesSearchForm.vue';
import AfterForm from '@/components/Sales/AfterForm.vue';
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'After',
	components:{
		SalesSearchForm,AfterForm
	},
	data(){
		return{
			tableData,afterDialogVisible,isFristShowAfterDialog,afterDialogTitle,
			isModify,isAdd,entryBtnTitle
		}
	},
	methods:{
		//点击开具售后单按钮事件
		addBtnEvent(){
			this.isAdd = true;
			this.afterDialogVisible = true;
			this.isModify = true;
			this.afterDialogTitle = "填写销售售后单信息";
			this.entryBtnTitle = '确定添加';
			if(!this.isFristShowAfterDialog){
				let propsFormDataTemp = {
					saId: "",sqId: "",cause: "",agent: "",agentDate: "",results: "",
					priority: "低",state: "",abstracts: "",belongDepartment: "",
					solution: "",feedback: "",satisfaction: "",
					isVisit:false,improvement: "",customer:""
				};
				this.$refs.afterFormData.formData = propsFormDataTemp;
			}
			this.showAfterDialog();
		},
		//点击查看按钮事件
		seeBtnEvent(row){
			this.isModify = false;
			this.propsFormDataSaId = row.saId;
			console.log(row);
			this.afterDialogTitle = "销售售后单信息";
			if(!this.isFristShowAfterDialog){
				this.$refs.afterFormData.queryFun(this.propsFormDataSaId);	
			}
			this.showAfterDialog();
		},
		//点击修改按钮事件
		modifyBtnEvnet(row){
			this.isModify = true;
			this.propsFormDataSaId = row.saId;
			this.afterDialogTitle = "修改销售售后单信息";
			this.entryBtnTitle = '确定添加';
			if(!this.isFristShowAfterDialog){
				this.$refs.afterFormData.queryFun(this.propsFormDataSaId);	
			}
			this.showAfterDialog();
		},
		showAfterDialog(){
			if(this.isFristShowAfterDialog){
				this.isFristShowAfterDialog = false;
			}
			this.afterDialogVisible = true;
		},
		//点击确定新增/修改按钮
		entryAddOrModifyEvent(){
			this.$confirm('请谨慎操作。','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				console.log("点击确定新增/修改按钮");
				let data = this.$refs.afterFormData.formData;
				this.submitAfterData(data);
				console.log(data);
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
		showTagType(isVisit){
			return isVisit == 1?'是':'否';
		},
		tagType(isVisit){
			return isVisit == 1?'success':'warning';
		},
		filterType(value, row) {
			return row.isVisit == value;
		},
		//提交售后单数据
		submitAfterData(data){
			delete data.agentDate;
			delete data.applyDate;
			if(data.isVisit == true) data.isVisit = 1;
			else data.isVisit = 0;
			console.log(data);
			submitDataForPost("/sales-after/insert-update",data).then(res=>{
				console.log("售后数据返回。。。");
				console.log(res.data.data);
				let result = res.data.data;
				let msg = '';
				if(data.saId == ''){
					this.tableData.unshift(result);
					msg = '开具售后单成功！';
				}
				else{
					msg = '更新售后单成功！';
					result.applyDate = data.applyDate;
					for(let i=0;i<this.tableData.length;i++){
						if(this.tableData[i].saId == data.saId){
							console.log("匹配到数据");
							this.tableData[i] = result;
							break;
						}
					}
				}
				this.afterDialogVisible = false;
				this.$message({
					type: 'success',
					message: msg
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '开具售后单失败!'
				});
			});
		},
		//销售售后单页面打开时拉取信息
		getInitInfo(){
			let params = {
				agent:null
			};
			console.log(params);
			GetForParams("/sales-after/all",params).then(res=>{
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
	height: 550px;
	overflow: auto;
}

</style>
