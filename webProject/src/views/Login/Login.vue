<template>
	<div id="login-page">
		<div id="top-info">重庆胜梵科技公司管理信息系统</div>
		<div class="login" v-if="!isModifyPwd">
			<div class="header">登录</div>
			<el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
				<label>工号：</label>
				<el-form-item prop="name">
					<el-input class="input-item" v-model="ruleForm.name" placeholder="请输入工号"></el-input>
				</el-form-item>
				
				<label>密码：</label>
				<el-form-item prop="pwd">
					<el-input class="input-item" v-model="ruleForm.pwd" placeholder="演示密码:123456" show-password></el-input>
				</el-form-item>
				<el-form-item>
				<el-button type="primary" class="login_btn" @click="submitForm('ruleForm')">登录</el-button>
				</el-form-item>
				<label id="forgotPwd" @click="forgotPwdEvent()">忘记密码？</label>
				<label id="hintPwd" @click="dialogVisible = true">各权限工号</label>
			</el-form>
		</div>
		<div class="login" v-if="isModifyPwd">
			<div class="header">修改密码</div>
			<el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
				<label>用户名：</label>
				<el-form-item prop="name">
					<el-input class="input-item" :disabled="true" v-model="ruleForm.name" placeholder="请输入用户名"></el-input>
				</el-form-item>
				
				<label>新密码：</label>
				<el-form-item prop="pwd">
					<el-input class="input-item" v-model="ruleForm.pwd" placeholder="密码必须大于6位" show-password></el-input>
				</el-form-item>
				<label>重复密码：</label>
				<el-form-item prop="pwd1">
					<el-input class="input-item" v-model="ruleForm.pwd1" placeholder="再次输入密码" show-password></el-input>
				</el-form-item>
				<el-form-item>
				<el-button type="primary" class="login_btn" @click="modifyPwd('ruleForm')">确认修改</el-button>
				</el-form-item>
			</el-form>
		</div>
	
		<footer>版权所有：91721380233&nbsp;毕术桂&nbsp;&nbsp;
			IPC备案：<a href="https://beian.miit.gov.cn/" target="_blank">
				渝ICP备17008870号
			</a>
		</footer>
		<div id="hintLoginUser">
			<el-dialog
				title="各权限工号:"
				width="600px"
				v-model="dialogVisible">
				<div>
					<p>超级管理：具备所有权限,user：2100025</p>
					<p>客户管理：业务组 2100017，管理组 2100018</p>
					<P>人事管理：招聘组 2100022，管理组 2100023，人事组 2100024</P>
					<p>采购管理：采购组 2100019，管理组 2100020，询价组 2100021</p>
					<P>销售管理：报价组 2100026，售后组 2100027，管理组 2100028</P>
					<P>仓储管理：入库组 2100029，出库组 2100030，管理组 2100031</P>
					<P>财务管理：会计组 2100014，审核组 2100015，登账组 2100016，管理组 2100032</P>
				</div>
			</el-dialog>
		</div>
		
	</div>
	<router-view></router-view>
</template>

<script>
let dialogVisible = true;
import {reactive,computed,ref,isRef,toRefs,readonly} from 'vue';
import {onMounted} from 'vue';
import {getDataForNullParams,GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default {
	name: 'Home',
	components: {
		
	},
	data(){
		let isModifyPwd = false;
		const ruleForm = {
			name: '',
			pwd:'',
			pwd1:''
		};
		const rules = {
			name: [
				{ required: true, message: '请输入用户名', trigger: 'blur' }
			],
			pwd: [
				{ required: true, message: '请输入密码', trigger: 'blur' },
			],
			pwd1: [
				{ required: true, message: '请输入密码', trigger: 'blur' },
			],
		};
		return{
			ruleForm,rules,isModifyPwd,dialogVisible
		}
	},
	methods:{
		forgotPwdEvent(){
			this.$alert("请联系人事专员进行密码重置，联系方式：13101349636");
		},
		submitForm(formName){
			console.log(formName);
			this.$refs[formName].validate((valid) => {
				if (valid) {
					//this.ruleForm.pwd = '';
					//this.isModifyPwd = true;
					// this.$router.push('/index');
					console.log('true');
					this.submitEmployeeData();
				} else {
					console.log('error submit!!');
					return false;
				}
			});	
		},
		submitEmployeeData(){
			let data = {
				employeeId:this.ruleForm.name,
				pwd:this.ruleForm.pwd
			};
			console.log(data);
			submitDataForPost("/employee/login",data).then(res=>{
				if(res.data.data.msg == 'success'){
					// console.log("res.data.data",res.data.data);
					this.$store.commit('addEmployee',res.data.data.employee);
					this.$store.state.employeeId = res.data.data.employee.employeeId;
					this.$store.commit('addTabs', {route: '/dashboard/workplace', name: 'workplace'});
					this.$store.commit('setActiveIndex', '/dashboard/workplace');
					// console.log("employee",this.$store.getters.employee);
					this.$router.push('/dashboard/workplace');
				}else if(res.data.data.msg == 'error'){
					this.$message({
						type: 'error',
						message: '密码错误'
					});
				}else if(res.data.data.msg == 'warning'){
					// console.log(res.data.data);
					this.$store.commit('addEmployee',res.data.data.employee);
					this.$store.state.employeeId = res.data.data.employee.employeeId;
					this.$store.commit('addTabs', {route: '/dashboard/workplace', name: 'workplace'});
					this.$store.commit('setActiveIndex', '/dashboard/workplace');
					 this.$alert('您为首次登录，将进入账号管理页面修改密码', '提示', {
					  confirmButtonText: '确定',
					  callback: action => {
						this.$router.push('/center/self');
					  }
					});
				}else{
					this.$message({
						type: 'error',
						message: res.data.data
					});
				}
			}).catch(err=>{
				this.$message({
					type: 'error',
					message: '登录失败!'
				});
				// location.reload();
			});
		},
		resetForm(formName){
			this.$refs[formName].resetFields();
		},
		modifyPwd(formName){
			console.log(formName);
			this.$refs[formName].validate((valid) => {
				if (valid) {
					this.ruleForm.pwd = '';
					this.isModifyPwd = true;
				} else {
					console.log('error submit!!');
					return false;
				}
			});	
		}
	}
}
</script>

<style lang="scss">
#hintLoginUser{
	.el-dialog__body{
		padding: 10px 20px;
	}
}
#forgotPwd{
	font-size: 14px;
	color: #409EFF;
	margin-top: -15px;
	cursor: pointer;
	float: left;
	display: block;
}
#hintPwd{
	font-size: 14px;
	color: #409EFF;
	margin-top: -15px;
	cursor: pointer;
	display: block;
	float: right;
}
#login-page footer{
	position: fixed;
	bottom: 50px;
	color: #fff;
	width: 100%;
	text-align: center;
	a{
		color: #fff;
		text-decoration: none;
	}
}
#top-info{
	position: fixed;
	top: 50px;
	width: 100%;
	font-size: 36px;
	text-align: center;
	color: #fff;
}
#login-page{
	background: #5490e6 url(../../assets/img/bg2.png) center bottom;
	background-repeat: no-repeat;
	background-size: cover;
	width: 100%;
	height: 100%;
}
.login{
	width: 250px;
	min-height: 450px;
	padding: 0 50px;
	border-radius: 15px;
	position: relative;
	left: 50%;
	transform: translate(-50%,45%);
	// border: 1px #42B983 solid;
	background-color: #fff;
	
}
.header{
	font-size: 30px;
	font-weight: bold;
	text-align: center;
	line-height: 150px;
}
.demo-ruleForm label{
	font-size: 16px;
	display: block;
	float: left;
	color: #2c3e50;
	margin-bottom: 5px;
}
.login_btn{
	width: 250px;
	margin-top: 20px;
}
</style>
