<template>
	<div class="recruit-main">
		<el-form :inline="true" v-model="selectFromData"  class="demo-form-inline">
			<el-form-item label="所属部门:">
			  <el-select v-model="selectFromData.departmentId" class="el-select-department" placeholder="所属部门">
			    <el-option v-for="(item,index) in departmentData" :key="index" :label="item.title" :value="item.departmentId"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="发布时期:">
				<el-date-picker
				      v-model="selectDateTemp"
				      type="daterange"
				      align="right"
				      unlink-panels
				      range-separator="至"
				      start-placeholder="开始日期"
				      end-placeholder="结束日期"
				      :shortcuts="shortcuts"
				></el-date-picker>
			</el-form-item>
		    <el-button type="primary" @click="selectBtnEvent()">查询</el-button>
		   <el-button type="info" @click="selectClearBtnEvent()">清除查询</el-button>
		 
		</el-form>
		
		<div class="recruit-user-btn">
			<el-button type="primary" @click="addApplyBtnEvent()" size="small" icon="el-icon-plus">招聘申请</el-button>
		</div>
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border
			:cell-style="tableRowClass">
			<el-table-column fixed prop="department.title" label="申请部门" width="200"></el-table-column>
			<el-table-column prop="recruitId" label="编号" width="75"></el-table-column>
			<el-table-column prop="employees.name" label="申请人" width="100"></el-table-column>
			<el-table-column prop="applyDate" label="申请日期" width="200"></el-table-column>
			<el-table-column prop="applyState" label="申请状态" width="100"
				:filters="[{ text: '已执行', value: '已执行' }, { text: '已申请', value: '已申请' }]"
				:filter-method="filterState">
				<template #default="scope">
					<el-tag
					  :type="scope.row.applyState == '已执行' ? 'primary' : 'success'"
					  disable-transitions>{{scope.row.applyState}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="post" label="招聘职位"></el-table-column>
			<el-table-column prop="num" label="需求人数" width="100"></el-table-column>
			<el-table-column prop="speciality" label="要求专业"></el-table-column>
			<el-table-column prop="updateDate" label="经手日期" width="200"></el-table-column>
			<el-table-column prop="updateId" label="经手人" width="100"></el-table-column>
			<el-table-column fixed="right" label="操作" width="120">
				<template #default="scope">
					<el-button
					  @click.native.prevent="lookInfoBtnEvent(scope.row)"
					  type="text"
					  size="small">
					  查看详情
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<!-- <div class="employee-page">招聘管理分页选择器</div> -->
		
		<el-dialog
		  title="填写招聘信息"
		  v-model="dialogVisible"
		  width="40%"
		  :before-close="handleClose">
		  <RecruitApply ref="propFormData" :currentOperationId="currentOperationId"></RecruitApply>
		  <template #footer>
			<span>
		      <el-button @click="dialogVisible = false">取 消</el-button>
			  <el-button v-show="isImplementOperation" type="primary" @click="entryImplementBtnEvent()">执行申请</el-button>
		      <el-button v-show="isAddOperation" type="success" @click="entryApplyBtnEvent()">确定申请</el-button>
		    </span>
		  </template>
		</el-dialog>
	</div>
</template>

<script>
let departmentData = [];
let tableData = [];
let isFristShowDialog = true;
let isAddOperation = false;
let isImplementOperation = false;
let dialogVisible = false;
let currentOperationId = null;
//=======搜索=======
let selectFromData = {departmentId:'',startDate:'',endDate:''}; 
let selectDateTemp = '';
let shortcuts= [{
  text: '最近一周',
  value: (() => {
	const end = new Date()
	const start = new Date()
	start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
	return [start, end]
  })(),
}, {
  text: '最近一个月',
  value: (() => {
	const end = new Date()
	const start = new Date()
	start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
	return [start, end]
  })(),
}, {
  text: '最近三个月',
  value: (() => {
	const end = new Date()
	const start = new Date()
	start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
	return [start, end]
  })(),
}];
import RecruitApply from '@/components/Personal/RecruitApply.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'Recriut',
	components:{
		RecruitApply
	},
	data(){
		return{
			selectFromData,selectDateTemp,shortcuts,departmentData,
			tableData,isFristShowDialog,isAddOperation,dialogVisible,currentOperationId,
		}
	},
	methods:{
		//点击清空
		selectClearBtnEvent(){
			this.selectFromData = {departmentId:'',startDate:'',endDate:''}; 
			this.selectDateTemp = '';
			this.getInitInfo();
		},
		//点击搜索按钮
		selectBtnEvent(){
			if(this.selectFromData.departmentId == '' && this.selectDateTemp == ''){
				this.$message({
					type: 'error',
					message: '请输入查询条件'
				});
				return;
			}
			//处理选择的日期
			if(this.selectDateTemp != ''){
				this.selectFromData.startDate = this.dateToString(this.selectDateTemp[0]) + ' 00:00:00';
				this.selectFromData.endDate = this.dateToString(this.selectDateTemp[1]) + ' 23:59:59';
			}
			console.log("selectFromData",this.selectFromData);
			this.getSelectResult(this.selectFromData);
		},
		dateToString(date){ 
		  let year = date.getFullYear(); 
		  let month =(date.getMonth() + 1).toString(); 
		  let day = (date.getDate()).toString();  
		  if (month.length == 1) { 
		      month = "0" + month; 
		  } 
		  if (day.length == 1) { 
		      day = "0" + day; 
		  }
		  let dateTime = year + "-" + month + "-" + day;
		  return dateTime; 
		},
		//获取查询结果
		async getSelectResult(params){
			GetForParams("/recruit/all-params",params).then(res=>{
				this.tableData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//拉取所有部门
		async initDepartment(){
			getDataForNullParams("/department/all").then(res=>{
				this.departmentData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		tableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		entryImplementBtnEvent(){
			this.$confirm('谨慎操作, 是否继续?', '提示', {
			  confirmButtonText: '继续',
			  cancelButtonText: '取消',
			  type: 'warning'
			}).then(() => {
				let data = {};
				data.recruitId = this.currentOperationId;
				data.updateId = this.$store.getters.employeeId;
				data.applyState = '已执行';
				this.submitExamineInfo(data);
			}).catch(() => {});
		},
		//点击申请按钮事件
		addApplyBtnEvent(){
			this.isAddOperation = true;
			this.isImplementOperation = false;
			this.currentOperationId = null;
			if(this.isFristShowDialog) this.isFristShowDialog = false;
			else this.$refs.propFormData.resetData();
			this.dialogVisible = true;
		},
		//点击查看详情按钮
		lookInfoBtnEvent(row){
			this.isAddOperation = false;
			this.isImplementOperation = true;
			this.currentOperationId = row.recruitId;
			if(this.isFristShowDialog) this.isFristShowDialog = false;
			else this.$refs.propFormData.queryFun(row.recruitId);
			this.dialogVisible = true;
		},
		addRecord(){
			let data = this.$refs.propFormData.formData;
			data.applyId = this.$store.getters.employeeId;
			data.applyState = '已申请';
			data.departmentId = this.$store.getters.employee.departmentId;
			// console.log(data);
			this.submitApplyInfo(data);
		},
		entryApplyBtnEvent(){
			this.$confirm('请仔细核对, 是否继续?', '提示', {
			  confirmButtonText: '核对无误',
			  cancelButtonText: '重新核对',
			  type: 'warning'
			}).then(() => {
				this.addRecord();
			}).catch(() => {});
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		filterState(value, row) {
			return row.applyState === value;
		},
		//提交发布
		submitExamineInfo(data){
			submitDataForPost("/recruit/implement",data).then(res=>{
				for(let i=0;i<this.tableData.length;i++){
					if(this.tableData[i].recruitId == data.recruitId){
						this.tableData[i].updateDate = res.data.data;
						this.tableData[i].applyState = data.applyState;
						this.tableData[i].updateId = data.updateId;
						break;
					}
				}
				this.dialogVisible = false;
				this.$message({
					type: 'success',
					message: '执行成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '执行过程错误!'
				});
			});
		},
		//提交申请
		submitApplyInfo(data){
			submitDataForPost("/recruit/insert",data).then(res=>{
				this.getOneRecord(res.data.data);
				this.dialogVisible = false;
				this.$message({
					type: 'success',
					message: '提交招聘申请成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '提交招聘申请过程错误!'
				});
			});
		},
		//拉取一条记录数据
		async getOneRecord(value){
			let params = {
				id:value
			};
			GetForParams("/recruit/one",params).then(res=>{
				this.tableData.unshift(res.data.data);
			}).catch(err=>{
				console.log(err);
			});
		},
		//页面打开时拉取所有信息
		async getInitInfo(){
			let params = {
				departmentId:null
			};
			GetForParams("/recruit/all",null).then(res=>{
				this.tableData = res.data.data;
				// console.log(this.tableData);
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		this.getInitInfo();
		this.initDepartment();
	}
}
</script>

<style lang="scss" scoped>
@import url("../../assets/css/Personal/Recruit.scss");
</style>
