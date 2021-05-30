<template>
	<div class="office-disk-main" id="department-position-info">
		<div>
			<label class="department-select-item">选择部门:
			  <el-select @change="changedepartment()" v-model="currentDepartmentId" class="el-select-sex" placeholder="未选择">
			    <el-option v-for="(item,index) in departmentData" :key="index" :label="item.title" :value="item.departmentId"></el-option>
			  </el-select>
			</label>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="addDepartmentBtnEvent" icon="el-icon-plus">添加部门</el-button>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="modifyDepartmentBtnEvent">修改当前部门</el-button>
		</div>
		<el-table id="position-table"
			:data="tableData"
			stripe
			border
			height="350"
			highlight-current-row
			@row-click="positionRowClickEvent"
			:cell-style="tableRowClass"
			row-key="positionId"
			:tree-props="{children: 'positionList'}">
			<el-table-column fixed prop="jobTitle" label="职位">
				<template #header>
					<label>职位</label>
					<el-button type="text" size="mini" @click="addRootPositionBtnEvent" icon="el-icon-plus">添加</el-button>
				</template>
			</el-table-column>
			<el-table-column prop="positionId" label="编号" width="100"></el-table-column>
			<el-table-column prop="duty" label="摘要" ></el-table-column>
			<el-table-column prop="updateId" label="经手人" width="100"></el-table-column>
			<el-table-column prop="updateDate" label="更新日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="220">
				<template #default="scope">
					<el-button
					  @click.native.prevent="addPositionBtnEvent(scope.row)"
					  type="success"
					  size="small">
					  添加
					</el-button>
					<el-button
					  @click.native.prevent="modifyPositionBtnEvent(scope.row)"
					  type="warning"
					  size="small">
					  修改
					</el-button>
					<el-button
					  @click.native.prevent="deletePositionBtnEvent(scope.row)"
					  type="danger"
					  size="small">
					  删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		
		<el-dialog
		  class="dialogClass"
		  :title="departmentDialogTitle"
		  v-model="departmentDialogVisible"
		  width="40%"
		  :before-close="handleClose">
		  <el-form label-position="left" v-model="departmentFormData"  class="demo-form-inline">
		  	<el-form-item label="部门名称:">
		  	  <el-input size="medium" v-model="departmentFormData.title" placeholder="填写部门名称"></el-input>
		  	</el-form-item>
		  	<el-form-item label="描述:">
		  	  <el-input type="textarea" size="medium" :autosize="{ minRows: 3, maxRows: 6}" v-model="departmentFormData.description" placeholder="描述部门"></el-input>
		  	</el-form-item>
		  </el-form>
		  <template #footer>
			<span>
		      <el-button @click="departmentDialogVisible = false">取 消</el-button>
			  <el-button type="primary" @click="entryAddOrUpdatedepartmentEvent()">{{departmentDialogBtnTitle}}</el-button>
		    </span>
		  </template>
		</el-dialog>
		
		<el-dialog
		  :title="positionDialogTitle"
		  v-model="positionDialogVisible"
		  width="40%"
		  :before-close="handleClose">
		  <el-form label-position="left" v-model="positionFormData"  class="demo-form-inline">
		  	<el-form-item label="职位名称:">
		  	  <el-input size="medium" v-model="positionFormData.jobTitle" placeholder="职位名称"></el-input>
		  	</el-form-item>
		  	<el-form-item label="职位描述:">
		  	  <el-input type="textarea" size="medium" :autosize="{ minRows: 3, maxRows: 6}" v-model="positionFormData.duty" placeholder="职位描述"></el-input>
		  	</el-form-item>
		  </el-form>
		  <template #footer>
			<span>
		      <el-button @click="positionDialogVisible = false">取 消</el-button>
			  <el-button type="primary" @click="entrySumbitPositionEvent()">确定提交</el-button>
		    </span>
		  </template>
		</el-dialog>
		

	</div>
</template>

<script>
let tableData = [];	//职位表
let productTableData = [];//物品表
const departmentData = [];//部门表
let departmentFormData = {
	departmentId:null,
	title:'',
	agentId:null,
	description:'',
	updateDate:''
};
let positionFormData = {};
let departmentDialogTitle = '';
let departmentDialogVisible = false;
let positionDialogTitle = '';
let positionDialogVisible = false;
let currentDepartmentId = '';
let departmentDialogBtnTitle = '';
let currentOperationPositionRow = {};
let currentpositionOperation = 0;//当前职位操作方式,0修改，1新增子职位
import {getDataForNullParams,GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'Department',
	data(){
		return{
			departmentData,departmentFormData,tableData,departmentDialogTitle,departmentDialogBtnTitle,
			departmentDialogVisible,positionDialogVisible,productTableData,
			positionDialogTitle,currentpositionOperation,positionFormData,currentOperationPositionRow,
			currentDepartmentId
		}
	},
	methods:{
		tableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
			}
		},
		//职位行点击事件
		positionRowClickEvent(row){
			this.currentOperationPositionRow = row;
		},
		//部门下拉列表变更时时
		changedepartment(){
			// console.log("currentDepartmentId:::" + this.currentDepartmentId);
			this.getPositionInfo(this.currentDepartmentId);
			this.productTableData = [];//清空产品列表
		},
		//点击添加部门按钮事件
		addDepartmentBtnEvent(){
			let departmentFormDataTemp = {
				departmentId:null,title:'',description:''
			};
			this.departmentFormData = departmentFormDataTemp;
			this.departmentDialogTitle = '填写新增部门信息';
			this.departmentDialogBtnTitle = '确定新增';
			this.departmentDialogVisible = true;
		},
		//点击修改当前部门按钮事件
		modifyDepartmentBtnEvent(){
			this.getOneDepartment(this.currentDepartmentId);
			this.departmentDialogTitle = '修改当前部门信息';
			this.departmentDialogBtnTitle = '确定修改';
			this.departmentDialogVisible = true;
		},
		//确定添加或者更新部门事件
		entryAddOrUpdatedepartmentEvent(){
			this.departmentFormData.agentId = this.$store.getters.employeeId;
			delete this.departmentFormData.updateDate;
			this.submitDepartmentInfo(this.departmentFormData);
		},
		//确定提交部门结构数据
		entrySumbitPositionEvent(){
			// console.log(this.positionFormData);
			this.positionFormData.updateId = this.$store.getters.employeeId;
			this.submitpositionInfo(this.positionFormData);
		},
		//点击添加根职位事件
		addRootPositionBtnEvent(){
			let positionFormDataTemp = {
				positionId:null,
				jobTitle:'',
				duty:'',
				departmentId:this.currentDepartmentId,
			}
			this.positionFormData = positionFormDataTemp;
			this.positionDialogTitle = '填写新增根职位信息';
			this.positionDialogVisible = true;
			this.currentOperationPositionRow = this.tableData;
		},
		//点击添加子职位事件
		addPositionBtnEvent(row){
			this.currentOperationPositionRow = row;
			this.currentpositionOperation = 1;
			let positionFormDataTemp = {
				positionId:null,
				jobTitle:'',
				duty:'',
				departmentId:row.departmentId,
				parentId:row.positionId,
			}
			this.positionFormData = positionFormDataTemp;
			this.positionDialogTitle = '填写新增子职位信息';
			this.positionDialogVisible = true;
		},
		//点击修改职位事件
		modifyPositionBtnEvent(row){
			this.currentOperationPositionRow = row;
			this.currentpositionOperation = 0;
			let positionFormDataTemp = {
				positionId:row.positionId,
				jobTitle:row.jobTitle,
				parentId:row.parentId,
				duty:row.duty,
				departmentId:row.departmentId,
			}
			this.positionFormData = positionFormDataTemp;
			this.positionDialogTitle = '修改职位信息';
			this.positionDialogVisible = true;
		},
		//点击删除职位事件
		deletePositionBtnEvent(){
			
		},
		entryApply(){
			this.$confirm('开单后无法修改，请谨慎操作，是否开单？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				// done();
				this.inquiryDialogVisible = false;
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
		//提交职位的数据
		submitpositionInfo(data){
			submitDataForPost("/position/insert-update",data).then(res=>{
				let newPosition = res.data.data;
				if(data.positionId > 0){
					this.currentOperationPositionRow.jobTitle = newPosition.jobTitle;
					this.currentOperationPositionRow.duty = newPosition.duty;
					this.currentOperationPositionRow.updateDate = newPosition.updateDate;
					this.currentOperationPositionRow.updateId = newPosition.updateId;
				}else{
					newPosition.positionList = [];
					if(this.currentOperationPositionRow == this.tableData){
						this.tableData.unshift(newPosition);
					}else{
						this.currentOperationPositionRow.positionList.unshift(newPosition);
					}
				}
				this.positionDialogVisible = false;
				this.$message({
					type: 'success',
					message: '操作成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '操作失败!'
				});
			});
		},
		//提交部门数据
		submitDepartmentInfo(data){
			submitDataForPost("/department/insert-update",data).then(res=>{
				let newDepartment = res.data.data;
				if(Number(data.departmentId) > 0){
					for(let i=0;i<this.departmentData.length;i++){
						if(this.departmentData[i].departmentId == data.departmentId){
							this.departmentData[i] = newDepartment;
							break;
						}
					}
				}else{
					this.departmentData.push(newDepartment);
				}
				this.departmentDialogVisible = false;
				this.$message({
					type: 'success',
					message: '提交部门数据成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '提交部门数据失败!'
				});
			});
		},
		//拉取部门的树形结构信息
		async getPositionInfo(departmentId){
			let params = {};
			params.departmentId = departmentId;
			GetForParams("/position/all",params).then(res=>{
				this.tableData = res.data.data;
				// console.log(this.tableData);
			}).catch(err=>{
				console.log(err);
			});
		},
		//拉取单个部门信息
		async getOneDepartment(departmentId){
			let params = {};
			params.departmentId = departmentId;
			GetForParams("/department/one",params).then(res=>{
				this.departmentFormData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//拉取所有部门信息
		async initDepartment(){
			getDataForNullParams("/department/all").then(res=>{
				this.departmentData = res.data.data;
				if(this.departmentData.length > 0){
					this.getPositionInfo(this.departmentData[0].departmentId);
					this.currentDepartmentId = this.departmentData[0].departmentId;
				}
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		this.initDepartment();
	}
}
</script>

<style lang="scss">
@import url("../../assets/css/Public/public.scss");

.department-select-item{
	margin-right: 10px;
}
#department-position-info .el-dialog__body{
	padding-top: 10px;
	padding-bottom: 10px;
}
#department-info-header{
	width: 100%;
	height: 40px;
	color: #fff;
	text-align: center;
	line-height: 40px;
	font-size: 16px;
	background-color: #909399;
}
#department-info{
	width: 12%;
	height: 300px;
	float: left;
	border: 1px #ccc solid;
	border-radius: 8px;
	overflow: hidden;
}
#department-info li{
	list-style: none;
	list-style-type: none;
	cursor: pointer;
	line-height: 30px;
}
#position-table{

}
.activedepartmentItem{
	background-color: #67C23A;
	color: #ffffff;
}
#product-table{
	margin-top: 20px;
}
</style>
