<template>
	<div id="recruit-apply-main">
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<div>
			<el-form-item label="招聘岗位:">
			  <el-input size="medium" v-model="formData.post" placeholder="招聘岗位"></el-input>
			</el-form-item>
			<el-form-item label="需求人数:">
			  <el-input class="el-input-num" size="medium" v-model="formData.num" placeholder="需求人数"></el-input>
			</el-form-item>
			<el-form-item label="薪资范围:">
			  <el-input class="el-input-num" size="medium" v-model="formData.salary" placeholder="薪资范围"></el-input>
			</el-form-item>
			</div>
			<div class="el-item-textarea">
				<label id="el-item-textarea-label-frist">专业限制:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.speciality" placeholder="专业限制"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">能力要求:</label>
			  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.requirement" placeholder="能力要求"></el-input>
			</div>
			<div class="el-item-textarea">
				<label for="">岗位职责:</label>
				<el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.duty" placeholder="岗位职责"></el-input>
			</div>
		</el-form>
	</div>
</template>

<script>
let formData={recruitId:null,departmentId:null,post:'',num:null,speciality:'',
	duty:'',requirement:'',salary:''};
	
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'RecruitApply',
	props:{
		currentOperationId:Number
	},
	data(){
		return{
			formData
		}
	},
	methods:{
		resetData(){
			let formDataTemp = {recruitId:null,departmentId:null,post:'',num:null,speciality:'',
				duty:'',requirement:'',salary:''};
			this.formData = formDataTemp;
		},
		queryFun(id){
			this.getOneRecord(id);
		},
		//拉取一条记录数据
		async getOneRecord(value){
			let params = {
				id:value
			};
			GetForParams("/recruit/one",params).then(res=>{
				this.formData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
	},
	mounted() {
		if(this.currentOperationId == null) return;
		else this.getOneRecord(this.currentOperationId);
	}
}
</script>

<style lang="scss" scoped>
#recruit-apply-main{
	width: 100%;
	height: 370px;
	// background-color: #42B983;
	overflow:auto;
}
.el-input-num{
	// width: 75px;
}

.el-item-textarea{
	width: 100%;
}
.el-item-textarea label{
	display: block;
	padding-top: 20px;
	padding-bottom: 10px;
}
.el-item-textarea #el-item-textarea-label-frist{
	padding-top: 0px;
}
</style>
