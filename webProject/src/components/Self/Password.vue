<template>
	<div id="pwssword-view">
		
		<el-form v-model="employeeFormData" :label-position="left" class="demo-form-inline">
			<el-form-item label="工号:">
				<el-input readonly type="text" size="medium" v-model="employeeFormData.employeeId"></el-input>
			</el-form-item>
			<el-form-item label="原密码:">
				<el-input show-password type="text" size="medium" v-model="employeeFormData.pwd"></el-input>
			</el-form-item>
			<el-form-item label="新密码:">
				<el-input show-password type="text" size="medium" v-model="employeeFormData.newPwd"></el-input>
			</el-form-item>
			<el-form-item label="确定密码:">
				<el-input show-password type="text" size="medium" v-model="employeeFormData.newPwdTwo"></el-input>
			</el-form-item>
		</el-form>
	
		<div style="margin-top: 10px;">
			<el-button type="primary" @click="enrtyApplyBtnEvent('ruleForm')" icon="el-icon-refresh-right">更新密码</el-button>
		</div>
	
	</div>
</template>

<script>
let employeeFormData = {
	employeeId: '',
	pwd:'',
	newPwd:'',
	newPwdTwo:''
};

import {getDataForNullParams,GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'Password',
	data(){
		return{
			employeeFormData,
		}
	},
	methods:{
		enrtyApplyBtnEvent(formName){
			let data = {
				employeeId:this.$store.getters.employeeId,
				pwd:this.employeeFormData.pwd,
				newPwd:this.employeeFormData.newPwd,
				newPwdTwo:this.employeeFormData.newPwdTwo
			};
			if(data.pwd == ''){
				this.$message({
					type: 'warning',
					message: '必须输入原密码!'
				});
				return;
			}
			if(data.newPwd == ''){
				this.$message({
					type: 'warning',
					message: '新密码不得为空!'
				});
				return;
			}
			if(data.newPwd != data.newPwdTwo){
				this.$message({
					type: 'warning',
					message: '两次密码不同!'
				});
				return;
			}
			if(data.newPwd == data.pwd){
				this.$message({
					type: 'warning',
					message: '新密码与现密码禁止相同!'
				});
				return;
			}
			this.$confirm('是否确定更新密码?', '提示', {
			  confirmButtonText: '确定',
			  cancelButtonText: '取消',
			  type: 'warning'
			}).then(() => {
			  this.submitEmployeeData(data);
			}).catch();
		},
		submitEmployeeData(data){
			submitDataForPost("/employee/update-pwd",data).then(res=>{
				if(res.data.data == 'success'){
					this.$message({
						type: 'success',
						message: '更新密码成功'
					});
				}else{
					this.$message({
						type: 'error',
						message: res.data.data
					});
				}
				
				this.employeeFormData.pwd = '';
				this.employeeFormData.newPwd = '';
				this.employeeFormData.newPwdTwo = '';
			}).catch(err=>{
				this.$message({
					type: 'error',
					message: '更新失败!'
				});
				// location.reload();
			});
		},
	},
	mounted() {
		this.employeeFormData.employeeId = this.$store.getters.employeeId;
	}
}
</script>

<style lang="scss" scoped>
#pwssword-view{
	width: 300px;
	.el-form-item{
		margin-bottom: 0px;
	}
}
</style>
