<template>
	<div id="late-early-main">
		<SearchCondition></SearchCondition>
		<div class="recruit-user-btn">
			<el-button type="primary" @click="addLeaveBtnEvent()" size="small" icon="el-icon-plus">请假申请</el-button>
		</div>
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border
			:cell-style="tableRowClass">
			<el-table-column fixed prop="employees.name" label="姓名" width="100"></el-table-column>
			<el-table-column fixed prop="type" label="请假类型" width="100"
				:filters="filtersTable"
				:filter-method="typeFilterType">
				<template #default="scope">
					<el-tag
					  type="primary"
					  disable-transitions>{{scope.row.type}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column fixed prop="applyState" label="批准状态" width="100"
				:filters="[{ text: '待审核', value: '待审核' },{ text: '批准', value: '批准' },{ text: '未通过', value: '未通过' }]"
				:filter-method="stateFilterType">
				<template #default="scope">
					<el-tag
					  :type="tagType(scope.row.applyState)"
					  disable-transitions>{{scope.row.applyState}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="alrId" label="编号" width="75"></el-table-column>
			<el-table-column prop="employeeId" label="员工编号" width="100"></el-table-column>
			<el-table-column prop="startDate" label="开始时间" width="200"></el-table-column>
			<el-table-column prop="endDate" label="结束时间" width="200"></el-table-column>
			<el-table-column prop="applyDate" label="申请日期" width="200"></el-table-column>
			<el-table-column prop="cause" label="原因" width="300"></el-table-column>
			<el-table-column prop="note" label="备注" width="300"></el-table-column>
			<el-table-column prop="approvalDate" label="审核时间" width="200"></el-table-column>
			<el-table-column prop="approver" label="审核人" width="100"></el-table-column>
			<el-table-column fixed="right" label="操作" width="175">
				<template #default="scope">
					<el-button
						v-show="scope.row.applyState == '待审核'"
					  @click.native.prevent="approvalLeaveBtnEvent(scope.row)"
					  type="success"
					  size="small">
					  批准
					</el-button>
					<el-button
						v-show="scope.row.applyState == '待审核'"
					  @click.native.prevent="vetoLeaveBtnEvent(scope.row)"
					  type="danger"
					  size="small">
					  否决
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<Pagination></Pagination>
		
		<el-dialog
		  title="填写请假信息"
		  v-model="dialogVisible"
		  width="50%"
		  :before-close="handleClose">
			<ApplyLeaveForm ref="ApplyLeaveFormData"></ApplyLeaveForm>
		  <template #footer>
			<span>
		      <el-button @click="dialogVisible = false">取 消</el-button>
			  <el-button type="primary" @click="entryLeaveBtnEvent()">确定请假</el-button>
		    </span>
		  </template>
		</el-dialog>
	</div>
</template>

<script>
let filtersTable = [{ text: '事假', value: '事假' }, 
{ text: '病假', value: '病假' },{ text: '婚假', value: '婚假' },
{ text: '丧假', value: '丧假' },{ text: '年假', value: '年假' },{ text: '其他', value: '其他' }];
let tableData = [];

let dialogVisible = false;
let isFristShowDialog = true;
// import SearchCondition from '@/components/Personal/SearchCondition.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'PayableLedger',
	components:{
		
	},
	data(){
		return{
			tableData,dialogVisible,filtersTable,isFristShowDialog
		}
	},
	methods:{
		//批准请假事件
		approvalLeaveBtnEvent(row){
			let data = {};
			data.alrId = row.alrId;
			data.approver = this.$store.getters.employeeId;
			data.applyState = '批准';
			this.submitExamineInfo(data);
		},
		//否决请假事件
		vetoLeaveBtnEvent(row){
			let data = {};
			data.alrId = row.alrId;
			data.approver = this.$store.getters.employeeId;
			data.applyState = '未通过';
			this.submitExamineInfo(data);
		},
		//确定请假事件
		entryLeaveBtnEvent(){
			let data = this.$refs.ApplyLeaveFormData.formData;
			data.startDateTemp = this.myFormatDate(data.DateTemp[0]);
			data.endDateTemp = this.myFormatDate(data.DateTemp[1]);
			data.employeeId = this.$store.getters.employeeId;
			data.applyState = '待审核';
			//console.log(data);
			delete data.DateTemp;
			this.submitLeaveInfo(data);
		},
		//点击请假按钮事件
		addLeaveBtnEvent(){
			console.log(this.isFristShowDialog);
			if(this.isFristShowDialog) this.isFristShowDialog = false;
			else this.$refs.ApplyLeaveFormData.resetData();
			this.dialogVisible = true;
		},
		tableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		tagType(applyState){
			if(applyState == '待审核') return 'primary';
			else if(applyState == '批准') return 'success';
			else return 'warning';
		},
		typeFilterType(value, row) {
			return row.type === value;
		},
		stateFilterType(value, row) {
			return row.applyState === value;
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		//日期转为字符串
		myFormatDate(value) {
		　　let year=value.getFullYear();
		　　let month=value.getMonth()+1;
		　　let date=value.getDate();
		　　let hour=value.getHours();
		　　let minute=value.getMinutes();
		　　let second=value.getSeconds();
			if(month < 10) month = '0' + month;
			if(date < 10) date = '0' + date;
			if(hour < 10) hour = '0' + hour;
			if(minute < 10) minute = '0' + minute;
			if(second < 10) second = '0' + second;
		　　return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
		},
		//提交审核
		submitExamineInfo(data){
			submitDataForPost("/apply-leave-record/examine",data).then(res=>{
				for(let i=0;i<this.tableData.length;i++){
					if(this.tableData[i].alrId == data.alrId){
						this.tableData[i].approvalDate = res.data.data;
						this.tableData[i].applyState = data.applyState;
						this.tableData[i].approver = data.approver;
						break;
					}
				}
				this.$message({
					type: 'success',
					message: '审核成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '审核过程错误!'
				});
			});
		},
		//提交请假申请
		submitLeaveInfo(data){
			submitDataForPost("/apply-leave-record/insert",data).then(res=>{
				console.log(res.data.data);
				this.getOneRecord(res.data.data);
				this.dialogVisible = false;
				this.$message({
					type: 'success',
					message: '提交请假申请成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '提交请假申请过程错误!'
				});
			});
		},
		//拉取一条记录数据
		async getOneRecord(value){
			let params = {
				alrId:value
			};
			GetForParams("/apply-leave-record/one",params).then(res=>{
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
			GetForParams("/apply-leave-record/all",null).then(res=>{
				this.tableData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		// this.getInitInfo();
	}
}
</script>

<style lang="scss" scoped>

</style>
