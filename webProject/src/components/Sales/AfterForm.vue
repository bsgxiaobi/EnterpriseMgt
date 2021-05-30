<template>
	<div id="imquiry-form-main">
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<el-form-item label="请求客户:">
			  <el-input :readonly="!isModify" v-model="formData.customer" placeholder="请求客户"></el-input>
			</el-form-item>
			<el-form-item label="关联销售单:">
			  <el-input :readonly="!isModify" v-model="formData.sqId"></el-input>
			</el-form-item>
			<el-form-item label="请求摘要:">
			  <el-input :readonly="!isModify" v-model="formData.abstracts"></el-input>
			</el-form-item>
			<el-form-item label="优先级别:">
			  <el-select v-model="formData.priority" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="!isModify" label="高" value="高"></el-option>
			    <el-option :disabled="!isModify" label="中" value="中"></el-option>
				<el-option :disabled="!isModify" label="低" value="低"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="责任部门:">
			  <el-select v-model="formData.belongDepartment" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="!isModify" label="仓储部" value="仓储部"></el-option>
			    <el-option :disabled="!isModify" label="运输部" value="运输部"></el-option>
				<el-option :disabled="!isModify" label="销售部" value="销售部"></el-option>
				<el-option :disabled="!isModify" label="客户关系部" value="客户关系部"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="处理状态:">
			  <el-select v-model="formData.state" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="!isModify" label="已处理" value="已处理"></el-option>
			    <el-option :disabled="!isModify" label="处理中" value="处理中"></el-option>
				<el-option :disabled="!isModify" label="待处理" value="待处理"></el-option>
			  </el-select>
			</el-form-item>
			<div class="el-item-textarea">
				<label class="el-item-textarea-label-frist">请求原因:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.cause" placeholder="请求原因"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">解决方案:</label>
			  <el-input type="textarea" :readonly="!isModify" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.solution" placeholder="解决方案"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">处理结果:</label>
				<el-input type="textarea" :readonly="!isModify" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.results" placeholder="处理结果"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">反馈意见:</label>
				<el-input type="textarea" :readonly="!isModify" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.feedback" placeholder="反馈意见"></el-input>
			</div>
			
			<div>
				<el-form-item label="客户满意度:">
				  <el-select v-model="formData.satisfaction" class="el-select-sex" placeholder="未指定">
				    <el-option :disabled="!isModify" label="非常满意" value="非常满意"></el-option>
				    <el-option :disabled="!isModify" label="满意" value="满意"></el-option>
					<el-option :disabled="!isModify" label="不满意" value="不满意"></el-option>
					<el-option :disabled="!isModify" label="非常不满意" value="非常不满意"></el-option>
				  </el-select>
				</el-form-item>
				<el-form-item label="是否回访:">
					<el-switch  :readonly="!isModify" v-model="formData.isVisit" active-text="是" inactive-text="否" active-color="#13ce66"></el-switch>
				</el-form-item>
			</div>
			
			<div class="el-item-textarea">
				<label for="" class="el-item-textarea-label-frist">改进意见:</label>
				<el-input type="textarea" :readonly="!isModify" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.improvement" placeholder="岗位职责"></el-input>
			</div>
		</el-form>
	</div>
</template>

<script>
let formData = {
	saId: "",
	sqId: "",
	cause: "",
	agent: "",
	agentDate: "",
	results: "",
	priority: "低",
	state: "",
	abstracts: "",
	belongDepartment: "",
	solution: "",
	feedback: "",
	satisfaction: "",
	isVisit: false,
	improvement: "",
	customer:""
};

import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'After',
	components:{
		componentIsVisit(){
			return function(i){
				return i==1?true:false;
			}
		}
	},
	props:{
		isAdd:Boolean,
		isModify:Boolean,
		propsFormDataSaId:Object
	},
	data(){
		return{
			formData
		}
	},
	methods:{
		queryFun(saId){
			console.log("saId::::" + saId);
			this.getOneAfter(saId);
		},
		getOneAfter(saId){
			let params = {
				saId:saId
			};
			console.log(params);
			GetForParams("/sales-after/one",params).then(res=>{
				this.formData = res.data.data;
				console.log(this.tableData);
			}).catch(err=>{
				console.log(err);
			});
		},
	},
	mounted() {
		if(this.isAdd){
			this.formData.agent = this.$store.getters.employeeId;
			return;
		}
		//查看或者修改
		this.getOneAfter(this.propsFormDataSaId);
	}
}
</script>

<style lang="scss">
.el-item-textarea{
	width: 100%;
	margin-bottom: 22px;;
}
.el-item-textarea label{
	display: block;
	padding-bottom: 10px;
}
.el-item-textarea .el-item-textarea-label-frist{
	padding-top: 0px;
}
</style>
