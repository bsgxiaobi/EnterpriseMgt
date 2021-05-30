<template>
	<div class="rewards-punishments-main">
		<SearchCondition></SearchCondition>
		<div class="recruit-user-btn">
			<el-button type="primary" @click="addApplyBtnEvent()" size="small" icon="el-icon-plus">添加奖惩</el-button>
		</div>
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border
			:cell-style="tableRowClass"><el-table-column fixed prop="employees.name" label="姓名" width="100"></el-table-column>
			<el-table-column fixed prop="type" label="类型" width="100"
				:filters="[{ text: '奖励', value: '奖励' }, { text: '惩罚', value: '惩罚' }]"
				:filter-method="filterType">
				<template #default="scope">
					<el-tag
					  :type="scope.row.type == '惩罚' ? 'warning' : 'success'"
					  disable-transitions>{{scope.row.type}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="rwrId" label="编号" width="75"></el-table-column>
			<el-table-column prop="employeeId" label="员工编号" width="100"></el-table-column>
			<el-table-column prop="applyState" label="状态" width="100"
				:filters="[{ text: '已执行', value: '已执行' }, { text: '已撤销', value: '已撤销' }]"
				:filter-method="filterState">
				<template #default="scope">
					<el-tag
					  :type="scope.row.applyState == '已撤销' ? 'primary' : 'success'"
					  disable-transitions>{{scope.row.applyState}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="employees.department.title" label="所在部门"></el-table-column>
			<el-table-column prop="applyDate" label="发布日期" width="200"></el-table-column>
			<el-table-column prop="category" label="类别" width="150"></el-table-column>
			<el-table-column prop="way" label="方式" width="100"></el-table-column>
			<el-table-column prop="amount" label="金额" width="75"></el-table-column>
			<el-table-column prop="cause" label="详细" width="300"></el-table-column>
			<el-table-column prop="note" label="备注" width="300"></el-table-column>
			<el-table-column prop="applyId" label="发布者" width="100"></el-table-column>
			<el-table-column prop="updateId" label="更新者" width="100"></el-table-column>
			<el-table-column prop="updateDate" label="更新日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="100">
				<template #default="scope">
					<el-button
						v-show="scope.row.applyState == '已执行'"
					  @click.native.prevent="approvalApplyBtnEvent(scope.row)"
					  type="warning"
					  size="small">
					  撤销
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<el-dialog
		  title="填写奖惩信息"
		  v-model="dialogVisible"
		  width="40%"
		  :before-close="handleClose">
		  <AddRewardsPunishments ref="AddInfo"></AddRewardsPunishments>
		  <template #footer>
			<span>
		      <el-button @click="dialogVisible = false">取 消</el-button>
			  <el-button type="primary" @click="entryApplyBtnEvent()">添加</el-button>
		    </span>
		  </template>
		</el-dialog>
	</div>
</template>

<script>
let tableData = [];
let dialogVisible = false;
let isFristShowDialog = true;
let isAddOperation = false;
import SearchCondition from '@/components/Personal/SearchCondition.vue';
import AddRewardsPunishments from '@/components/Personal/AddRewardsPunishments.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'Rewards-Punishments',
	components:{
		AddRewardsPunishments,SearchCondition
	},
	data(){
		return{
			tableData,dialogVisible,isFristShowDialog,isAddOperation
		}
	},
	methods:{
		tableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		approvalApplyBtnEvent(row){
			this.$confirm('谨慎操作, 是否继续?', '提示', {
			  confirmButtonText: '继续',
			  cancelButtonText: '取消',
			  type: 'warning'
			}).then(() => {
				let data = {};
				data.rwrId = row.rwrId;
				data.updateId = this.$store.getters.employeeId;
				data.applyState = '已撤销';
				console.log(row);
				this.submitExamineInfo(data);
			}).catch(() => {});
		},
		addRecord(){
			let data = this.$refs.AddInfo.formData;
			data.applyId = this.$store.getters.employeeId;
			data.applyState = '已执行';
			console.log(data);
			this.submitApplyInfo(data);
		},
		//点击申请按钮事件
		addApplyBtnEvent(){
			this.isAddOperation = true;
			if(this.isFristShowDialog) this.isFristShowDialog = false;
			else this.$refs.AddInfo.resetData();
			this.dialogVisible = true;
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
		filterType(value, row) {
			return row.type === value;
		},
		filterState(value, row) {
			return row.applyState === value;
		},
		//提交撤销
		submitExamineInfo(data){
			submitDataForPost("/rewards-punish-record/revoke",data).then(res=>{
				for(let i=0;i<this.tableData.length;i++){
					if(this.tableData[i].rwrId == data.rwrId){
						this.tableData[i].updateDate = res.data.data;
						this.tableData[i].applyState = data.applyState;
						this.tableData[i].updateId = data.updateId;
						break;
					}
				}
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
			submitDataForPost("/rewards-punish-record/insert",data).then(res=>{
				//console.log(res.data.data);
				this.getOneRecord(res.data.data);
				this.dialogVisible = false;
				this.$message({
					type: 'success',
					message: '提交奖惩申请成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '提交奖惩申请过程错误!'
				});
			});
		},
		//拉取一条记录数据
		async getOneRecord(value){
			let params = {
				id:value
			};
			GetForParams("/rewards-punish-record/one",params).then(res=>{
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
			GetForParams("/rewards-punish-record/all",null).then(res=>{
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

<style lang="scss" scoped>
@import url("../../assets/css/Personal/Rewards-Punishments.scss");
</style>
