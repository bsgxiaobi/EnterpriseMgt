<template>
	<div>
		
		<el-form :inline="true" v-model="employeeFormData"  class="demo-form-inline">
			<el-form-item label="姓名:">
				<el-input  type="text" size="medium" v-model="employeeFormData.name" placeholder="姓名"></el-input>
			</el-form-item>
			<el-form-item label="性别:">
			  <el-select v-model="employeeFormData.sex" placeholder="请选择">
				<el-option label="男" value="男"></el-option>
				<el-option label="女" value="女"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="工号:">
				<el-input readonly type="text" size="medium" v-model="employeeFormData.employeeId"></el-input>
			</el-form-item>
			<el-form-item label="部门:">
				<el-input readonly type="text" size="medium" v-model="employeeFormData.department.title"></el-input>
			</el-form-item>
			<el-form-item label="职位:">
				<el-input readonly type="text" size="medium" v-model="employeeFormData.position.jobTitle" placeholder="姓名"></el-input>
			</el-form-item>
			<el-form-item label="手机:">
				<el-input type="text" size="medium" v-model="employeeFormData.phone" placeholder="手机"></el-input>
			</el-form-item>
			<el-form-item label="邮箱:">
				<el-input type="text" size="medium" v-model="employeeFormData.email" placeholder="邮箱"></el-input>
			</el-form-item>
			<el-form-item label="民族:">
				<el-input type="text" size="medium" v-model="employeeFormData.national" placeholder="民族"></el-input>
			</el-form-item>
			<el-form-item label="身份证号:">
				<el-input type="text" size="medium" v-model="employeeFormData.cardId" placeholder="身份证号"></el-input>
			</el-form-item>
		</el-form>
		
		<el-divider content-position="left">档案详情</el-divider>
		<el-form :inline="true" v-model="archivesFormData"  class="demo-form-inline">
			<el-form-item label="合同开始日期:">
				<el-input readonly type="text" size="medium" v-model="archivesFormData.contractStartTemp" placeholder="身份证号"></el-input>
			</el-form-item>
			<el-form-item label="合同期限:">
				<el-input readonly type="text" size="medium" v-model="archivesFormData.term" placeholder="合同期限"></el-input>
			</el-form-item>
			<el-form-item label="试用期:">
				<el-input readonly type="text" size="medium" v-model="archivesFormData.trialPeriod" placeholder="身份证号"></el-input>
			</el-form-item>
			<el-form-item label="签订工资(月):">
				<el-input readonly type="text" size="medium" v-model="archivesFormData.formalSalary" placeholder="工资/月"></el-input>
			</el-form-item>
			<el-form-item label="聘用渠道:">
				<el-input readonly type="text" size="medium" v-model="archivesFormData.channel" placeholder="聘用渠道"></el-input>
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
		<div style="margin-bottom: 10px;">
			<el-button type="primary" @click="enrtyApplyBtnEvent()" icon="el-icon-refresh-right">更新信息</el-button>
		</div>
	
	</div>
</template>

<script>
let employeeFormData = {
	employeeId: '',
	department:{
		title:''
	},
	position:{
		jobTitle:''
	},
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
import {getDataForNullParams,GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'Info',
	data(){
		return{
			employeeFormData,archivesFormData
		}
	},
	methods:{
		enrtyApplyBtnEvent(){
			let employeeData = {
				employeeId:this.$store.getters.employeeId,
				name:this.employeeFormData.name,
				sex:this.employeeFormData.sex,
				phone:this.employeeFormData.phone,
				cardId:this.employeeFormData.cardId,
				email:this.employeeFormData.email,
				national:this.employeeFormData.national
			};
			let archivesData = {
				employeeId:this.$store.getters.employeeId,
				socialId:this.archivesFormData.socialId,
				housing:this.archivesFormData.housing,
				employmentId:this.archivesFormData.employmentId,
				academic: this.archivesFormData.academic,
				graduatedSchool: this.archivesFormData.graduatedSchool,
				speciality: this.archivesFormData.speciality
			};
			console.log(employeeData);
			console.log(archivesData);
			this.submitEmployeeData(employeeData);
			this.submitArchivesData(archivesData);
		},
		submitEmployeeData(data){
			submitDataForPost("/employee/insert-update",data).then(res=>{
				this.$message({
					type: 'success',
					message: '更新成功!'
				});
			}).catch(err=>{
				this.$message({
					type: 'error',
					message: '更新失败!'
				});
				// location.reload();
			});
		},
		submitArchivesData(data){
			submitDataForPost("/employees-archives/update",data).then(res=>{
				this.$message({
					type: 'success',
					message: '信息更新成功!'
				});
			}).catch(err=>{
				this.$message({
					type: 'success',
					message: '更新失败!'
				});
				// location.reload();
			});
		},
		async getArchives(){
			let params = {
				employeeId:this.$store.getters.employeeId
			};
			GetForParams("/employees-archives/one",params).then(res=>{
				this.archivesFormData = res.data.data;
				this.archivesFormData.contractStartTemp =  res.data.data.contractStart;
			}).catch(err=>{
				console.log(err);
			});
		},
		//页面打开时拉取所有信息
		async getInitInfo(){
			let params = {
				employeeId:this.$store.getters.employeeId
			};
			GetForParams("/employee/one",params).then(res=>{
				this.employeeFormData = res.data.data;
				console.log(this.employeeFormData);
			}).catch(err=>{
				console.log('err',err);
			});
			
		}
	},
	mounted() {
		this.getInitInfo();
		this.getArchives();
	}
}
</script>

<style>
</style>
