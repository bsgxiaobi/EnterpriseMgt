<template>
	<div>
		<el-form :inline="true" v-model="employeeFormData"  class="demo-form-inline">
			<el-form-item label="姓名:">
				<el-input :readonly="!isAdd" type="text" size="medium" v-model="employeeFormData.name" placeholder="姓名"></el-input>
			</el-form-item>
			<el-form-item :readonly="!isAdd" label="性别:">
			  <el-select v-model="employeeFormData.sex" placeholder="请选择">
				<el-option label="男" value="男"></el-option>
				<el-option label="女" value="女"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="部门:">
			  <el-select @change="selectDepartmentChange()" v-model="employeeFormData.departmentId" placeholder="请选择">
				<el-option v-for="item in departmentData" :label="item.title" :value="item.departmentId"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="职位:">
			  <el-select v-model="employeeFormData.positionId" placeholder="请选择">
				<el-option v-for="item in positionData" :label="item.jobTitle" :value="item.positionId"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="手机:">
				<el-input :readonly="!isAdd" type="text" size="medium" v-model="employeeFormData.phone" placeholder="手机"></el-input>
			</el-form-item>
			<el-form-item label="邮箱:">
				<el-input :readonly="!isAdd" type="text" size="medium" v-model="employeeFormData.email" placeholder="邮箱"></el-input>
			</el-form-item>
			<el-form-item label="民族:">
				<el-input :readonly="!isAdd" type="text" size="medium" v-model="employeeFormData.national" placeholder="民族"></el-input>
			</el-form-item>
			<el-form-item label="身份证号:">
				<el-input :readonly="!isAdd" type="text" size="medium" v-model="employeeFormData.cardId" placeholder="身份证号"></el-input>
			</el-form-item>
		</el-form>
		
		<el-divider content-position="left">档案详情</el-divider>
		<el-form :inline="true" v-model="archivesFormData"  class="demo-form-inline">
			<el-form-item label="合同开始日期:">
				<el-date-picker
					 :readonly="!isAdd"
			      v-model="archivesFormData.contractStartTemp"
			      type="date"
			      placeholder="选择日期">
			    </el-date-picker>
			</el-form-item>
			<el-form-item label="合同期限:">
				<el-input type="text" size="medium" v-model="archivesFormData.term" placeholder="合同期限"></el-input>
			</el-form-item>
			<el-form-item label="试用期:">
			  <el-select v-model="archivesFormData.trialPeriod" placeholder="请选择">
				<el-option label="一个月" value="一个月"></el-option>
				<el-option label="两个月" value="两个月"></el-option>
				<el-option label="三个月" value="三个月"></el-option>
				<el-option label="其他" value="其他"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="签订工资(月):">
				<el-input type="text" size="medium" v-model="archivesFormData.formalSalary" placeholder="工资/月"></el-input>
			</el-form-item>
			<el-form-item label="聘用渠道:">
				<el-input type="text" size="medium" v-model="archivesFormData.channel" placeholder="聘用渠道"></el-input>
			</el-form-item>
			<el-form-item label="社会保险号:">
				<el-input type="text" size="medium" v-model="archivesFormData.socialId" placeholder="社会保险号"></el-input>
			</el-form-item>
			<el-form-item label="住房公积金号:">
				<el-input type="text" size="medium" v-model="archivesFormData.housing" placeholder="住房公积金号"></el-input>
			</el-form-item>
			<el-form-item label="就业登记号:">
				<el-input type="text" size="medium" v-model="archivesFormData.employmentId" placeholder="就业登记号"></el-input>
			</el-form-item>
			<el-form-item label="最高学历:">
				<el-input type="text" size="medium" v-model="archivesFormData.academic" placeholder="最高学历"></el-input>
			</el-form-item>
			<el-form-item label="毕业学校:">
				<el-input type="text" size="medium" v-model="archivesFormData.graduatedSchool" placeholder="毕业学校"></el-input>
			</el-form-item>
			<el-form-item label="所学专业:">
				<el-input type="text" size="medium" v-model="archivesFormData.speciality" placeholder="所学专业"></el-input>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
let employeeFormData = {
	employeeId: '',
	departmentId:null,
	positionId:null,
	name:'',
	sex:'',
	phone:'',
	cardId:'',
	email:'',
	pwd:'',
	national:''
};
let archivesFormData = {
	contractStartTemp:'',
	employeeId: '',
	joinLeave:  '',
	contractStart: '',
	term:'',
	trialPeriod: '',
	channel: '',
	formalSalary: '',
	socialId: '',
	housing: '',
	employmentId: '',
	leaveDate: '',
	leaveCause: '',
	joinAgent: '',
	joinHandlingDate: '',
	leaveAgent: '',
	leaveHandlingDate: '',
	academic: '',
	graduatedSchool: '',
	speciality: '',
	resume: '',
};
let departmentData = [];
let positionData = [];
import {getDataForNullParams,GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'ApplyLeaveForm',
	props:{
		propId:Number,
		isAdd:Boolean
	},
	data(){
		return{
			employeeFormData,archivesFormData
		}
	},
	methods:{
		resetData(){
			let employeeFormDataTemp = {
				employeeId: '',
				departmentId:null,
				positionId:null,
				name:'',
				sex:'',
				phone:'',
				cardId:'',
				email:'',
				pwd:'',
				national:''
			};
			let archivesFormDataTemp = {
				contractStartTemp:'',
				contractEndTemp:'',
				employeeId: '',
				joinLeave:  '',
				contractStart: '',
				term:'',
				trialPeriod: '',
				channel: '',
				formalSalary: '',
				socialId: '',
				housing: '',
				employmentId: '',
				leaveDate: '',
				leaveCause: '',
				joinAgent: '',
				joinHandlingDate: '',
				leaveAgent: '',
				leaveHandlingDate: '',
				academic: '',
				graduatedSchool: '',
				speciality: '',
				resume: '',
			};
			this.employeeFormData = employeeFormDataTemp;
			this.archivesFormData = archivesFormDataTemp;
		},
		//部门下拉表变换时
		selectDepartmentChange(){
			// console.log('employeeFormData.departmentId',employeeFormData.departmentId);
			this.employeeFormData.positionId = null;
			this.positionData = [];
			this.getPosition(employeeFormData.departmentId);
		},
		queryFun(id){
			console.log('queryFun::' + id);
			this.getEmployee(id);
		},
		async getEmployee(value){
			let params = {
				employeeId:value
			};
			GetForParams("/employee/one",params).then(res=>{
				this.employeeFormData = res.data.data;
				//拉取档案
				this.getArchives(res.data.data.employeeId);
			}).catch(err=>{
				console.log(err);
			});
		},
		async getArchives(value){
			let params = {
				employeeId:value
			};
			GetForParams("/employees-archives/one",params).then(res=>{
				this.archivesFormData = res.data.data;
				this.archivesFormData.contractStartTemp =  res.data.data.contractStart;
			}).catch(err=>{
				console.log(err);
			});
		},
		//拉取职位
		async getPosition(value){
			let params = {
				departmentId:value
			};
			GetForParams("/position/all-position",params).then(res=>{
				this.positionData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//页面打开时拉取所有信息
		async getInitInfo(){
			getDataForNullParams("/department/all").then(res=>{
				this.departmentData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		this.getInitInfo();
		if(this.propId != null) this.queryFun(this.propId);
	}
}
</script>

<style lang="scss" scoped>
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
