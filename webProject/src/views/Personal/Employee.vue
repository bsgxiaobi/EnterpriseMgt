<template>
	<div class="employee-main">
		<!-- <el-form :inline="true" v-model="fromEmployee"  class="demo-form-inline">
			
		  <el-form-item label="员工编号:">
		    <el-input v-model="fromEmployee.id" placeholder="员工编号"></el-input>
		  </el-form-item>
		  <el-form-item label="员工姓名:">
		    <el-input v-model="fromEmployee.name" placeholder="员工姓名"></el-input>
		  </el-form-item>
		  <el-form-item label="性别:">
		    <el-select v-model="fromEmployee.sex" class="el-select-sex" placeholder="性别">
		      <el-option label="男" value="1"></el-option>
		      <el-option label="女" value="0"></el-option>
		    </el-select>
		  </el-form-item>
		  <el-form-item label="联系电话:">
		    <el-input v-model="fromEmployee.phone" placeholder="联系电话"></el-input>
		  </el-form-item>
		  <el-form-item label="所属部门:">
		    <el-select v-model="fromEmployee.department" class="el-select-department" placeholder="所属部门">
		      <el-option label="人力资源部" value="1"></el-option>
		      <el-option label="采购部" value="0"></el-option>
			  <el-option label="技术部" value="2"></el-option>
		    </el-select>
		  </el-form-item>
		  <el-form-item>
		    <el-button type="primary" @click="">查询</el-button>
		  </el-form-item>
		  
		  <el-collapse accordion>
		  	<el-collapse-item title="更多筛选条件">
				<el-form-item label="学历:">
					<el-select v-model="fromEmployee.academic"  class="el-select-academic" placeholder="学历">
					  <el-option label="博士" value="1"></el-option>
					  <el-option label="研究生(学硕)" value="1"></el-option>
					  <el-option label="研究生(专硕)" value="1"></el-option>
					  <el-option label="本科" value="1"></el-option>
					  <el-option label="专科" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="入职日期:">
					<el-date-picker
					      v-model="fromEmployee.joinDate"
					      type="daterange"
					      align="right"
					      unlink-panels
					      range-separator="至"
					      start-placeholder="开始日期"
					      end-placeholder="结束日期"
					      :shortcuts="shortcuts"
					    >
					    </el-date-picker>
				</el-form-item>
				<el-form-item label="所属职级:">
					<el-select v-model="fromEmployee.rank" class="el-select-rank" placeholder="所属职级">
					  <el-option label="经理" value="1"></el-option>
					  <el-option label="主管" value="1"></el-option>
					  <el-option label="项目主管" value="1"></el-option>
					  <el-option label="组长" value="1"></el-option>
					  <el-option label="无" value="0"></el-option>
					</el-select>
				</el-form-item>
		      </el-collapse-item>
		  </el-collapse>
		</el-form> -->
		
		<div>
			<el-button style="margin-bottom: 10px; margin-top: 10px;" type="primary" size="medium" @click="addApplyBtnEvent" icon="el-icon-plus">入职办理</el-button>
			<el-button style="margin-bottom: 10px; margin-top: 10px;" type="primary" size="medium" @click="modifyDepartmentBtnEvent" icon="el-icon-plus">离职申请</el-button>
		</div>
		
		<el-table
		    :data="tableData"
		    style="width: 100%"
			stripe
			border
			height="600px"
			:cell-style="tableRowClass">
		    <el-table-column fixed prop="employeeId" label="工号" width="100"></el-table-column>
		    <el-table-column prop="name" label="姓名" width="120"></el-table-column>
		    <el-table-column prop="sex" label="性别" width="100"></el-table-column>
		    <el-table-column prop="phone" label="电话" width="120"></el-table-column>
			<el-table-column prop="email" label="邮箱" width="200"></el-table-column>
			<el-table-column prop="cardId" label="身份证号"></el-table-column>
			<el-table-column prop="national" label="民族" width="120"></el-table-column>
			<el-table-column fixed="right" label="操作" width="120">
				<template #default="scope">
					<el-button
					  @click.native.prevent="detailedBtnEvent(scope.row)"
					  type="primary"
					  size="small">
					  查看详情
					</el-button>
				</template>
			</el-table-column>
	  </el-table>
	  <!-- <div class="employee-page">分页选择器</div> -->
	  
	  <el-dialog
	    :title="dialogTitle"
	    v-model="dialogVisible"
	    width="850px"
	    :before-close="handleClose">
	    <JoinForm ref="propFormData" :propId="currentOperationEmployeeId" :isAdd="isAddOperation"></JoinForm>
	    <template #footer>
	  	<span>
	        <el-button @click="dialogVisible = false">取 消</el-button>
			<el-button type="primary" v-show="isAddOperation" @click="entryApplyBtnEvent()">提交入职信息</el-button>
	      </span>
	    </template>
	  </el-dialog>
	</div>
</template>

<script>
let shortcuts = [
	{
		text: '本月',
		value: [new Date(), new Date()],
	}, 
	{
		text: '今年至今',
		value: (() => {
		const end = new Date()
		const start = new Date(new Date().getFullYear(), 0)
		return [start, end]
	})(),
	}, 
	{
		text: '最近六个月',
		value: (() => {
		const end = new Date()
		const start = new Date()
		start.setMonth(start.getMonth() - 6)
		return [start, end]
		})(),
	}
];

let tableData = [];
let dialogTitle = '';
let dialogVisible = false;
let isFristShowDialog = true;
let isAddOperation = false;
let currentOperationEmployeeId = null;

import JoinForm from '@/components/Personal/JoinForm.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'Employee',
	components:{
		JoinForm
	},
	data(){
		return{
			tableData,dialogVisible,isFristShowDialog,isAddOperation,dialogTitle,
			currentOperationEmployeeId
		}
	},
	methods:{
		//修改档案信息
		entryModifgyBtnEvent(){
			
		},
		//点击查看详情信息
		detailedBtnEvent(row){
			this.dialogTitle = '档案详情';
			this.isAddOperation = false;
			this.currentOperationEmployeeId = row.employeeId;
			if(this.isFristShowDialog) this.isFristShowDialog = false;
			else this.$refs.propFormData.queryFun(row.employeeId);
			this.dialogVisible = true;
		},
		//点击申请按钮事件
		addApplyBtnEvent(){
			this.dialogTitle = '填写入职信息';
			this.isAddOperation = true;
			this.currentOperationEmployeeId = null;
			if(this.isFristShowDialog) this.isFristShowDialog = false;
			else this.$refs.propFormData.resetData();
			this.dialogVisible = true;
		},
		addRecord(){
			let employeeData = this.$refs.propFormData.employeeFormData;
			delete employeeData.employeeId;
			employeeData.pwd = '123456';
			
			let archivesData = this.$refs.propFormData.archivesFormData;
			let str = JSON.stringify(archivesData.contractStartTemp);
			archivesData.contractStartTemp = str.substr(1,10);
			
			this.submitEmployeeInfo(employeeData,archivesData);
		},
		entryApplyBtnEvent(){
			this.$confirm('请仔细核对,一旦添加无法修改,是否继续?', '提示', {
			  confirmButtonText: '核对无误',
			  cancelButtonText: '重新核对',
			  type: 'warning'
			}).then(() => {
				this.addRecord();
			}).catch(() => {});
		},
		tableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		filterType(value, row) {
			return row.type === value;
		},
		filterState(value, row) {
			return row.applyState === value;
		},
		//提交员工表
		submitEmployeeInfo(data,archivesData){
			submitDataForPost("/employee/insert-update",data).then(res=>{
				console.log('提交员工表');
				if(archivesData != null){
					archivesData.employeeId = res.data.data.employeeId;
					this.insertEmployeeArchives(archivesData);
				}
				if(data.employeeId != null){
					this.tableData.unshift(res.data.data);
				}else{
					for(let i = 0;i < this.tableData.length;i++){
						if(this.tableData[i].employeeId == data.employeeId){
							this.tableData[i] = res.data.data;
							break;
						}
					}
				}
				this.dialogVisible = false;
				this.$message({
					type: 'success',
					message: '提交数据成功!'
				});
			}).catch(err=>{
				this.$message({
					type: 'error',
					message: '提交数据过程错误!'
				});
			});
		},
		//新增员工档案表
		insertEmployeeArchives(data){
			submitDataForPost("/employees-archives/insert",data).then(res=>{
				console.log('新增员工档案表');
				console.log(res.data.data);
				// this.getOneRecord(res.data.data);
				// this.dialogVisible = false;
			}).catch(err=>{
				console.log(err.data);
			});
		},
		//拉取一条记录数据
		async getOneRecord(value){
			let params = {
				id:value
			};
			GetForParams("/employee/one",params).then(res=>{
				this.tableData.unshift(res.data.data);
			}).catch(err=>{
				console.log(err);
			});
		},
		//页面打开时拉取所有信息
		async getInitInfo(){
			let params = {
				employeeId:null
			};
			GetForParams("/employee/all",null).then(res=>{
				this.tableData = res.data.data;
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

<style scoped>
@import url("../../assets/css/Personal/Employee.css");
</style>
