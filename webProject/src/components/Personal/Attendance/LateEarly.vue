<template>
	<div id="late-early-main">
		<SearchCondition></SearchCondition>
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border
			:cell-style="tableRowClass">
			<el-table-column prop="aid" label="编号" width="75"></el-table-column>
			<el-table-column fixed prop="employeeId" label="员工编号" width="100"></el-table-column>
			<el-table-column fixed prop="employees.name" label="姓名" width="100"></el-table-column>
			<el-table-column fixed prop="type" label="类型" width="100"
				:filters="[{ text: '正常', value: '正常' }, { text: '迟到', value: '迟到' },{ text: '早退', value: '早退' }]"
				:filter-method="filterType">
				<template #default="scope">
					<el-tag
					  :type="tagType(scope.row.type)"
					  disable-transitions>{{scope.row.type}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="updateDate" label="打卡时间" width="200"></el-table-column>
			<el-table-column prop="equipment" label="打卡机器" width="150"></el-table-column>
			<el-table-column prop="employees.sex" label="性别" width="50"></el-table-column>
			<el-table-column prop="employees.department.title" label="部门" width="150"></el-table-column>
			<el-table-column prop="represent" label="申诉理由" width="200"></el-table-column>
			<el-table-column prop="representDate" label="申诉日期" width="300"></el-table-column>
			<el-table-column prop="representState" label="申诉状态"></el-table-column>
			<el-table-column prop="agentId" label="批准人"></el-table-column>
			<el-table-column prop="agentDate" label="批准日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="120">
				<template #default="scope">
					<el-button
						v-show="scope.row.represent == '' && scope.row.type != '正常'"
					  @click.native.prevent="representBtnEvent(scope.row)"
					  type="warning"
					  size="small">
					  申述
					</el-button>
					<el-button
						v-show="scope.row.represent != '' && scope.row.agentId == ''"
					  @click.native.prevent="handleRepresentBtnEvent(scope.row)"
					  type="success"
					  size="small">
					  处理申诉
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<Pagination></Pagination>
		
		<el-dialog
		  title="请输入申诉理由"
		  v-model="dialogVisible"
		  width="30%"
		  :before-close="handleClose">
		  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="currentRepresentText" placeholder="请输入申诉理由"></el-input>
		  <template #footer>
		    <span class="dialog-footer">
		      <el-button @click="dialogVisible = false">取 消</el-button>
		      <el-button type="primary" @click="entryRepresentBtnEvent()">确定申诉</el-button>
		    </span>
		  </template>
		</el-dialog>
		
		<el-dialog
		  title="申诉处理"
		  v-model="handleDialogVisible"
		  width="30%"
		  :before-close="handleClose">
		  <el-input readonly type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="currentRepresentText" placeholder="请输入申诉理由"></el-input>
		  <template #footer>
		    <span class="dialog-footer">
		      <el-button @click="handleDialogVisible = false">取 消</el-button>
			  <el-button type="warning" @click="entryRejectHandleBtnEvent()">驳回申诉</el-button>
			  <el-button type="success" @click="entrySuccessHandleBtnEvent()">变更正常</el-button>
		    </span>
		  </template>
		</el-dialog>
	</div>
</template>

<script>
let tableData = [];
let dialogVisible = false;
let handleDialogVisible = false;
let currentRepresentText = '';
let currentOperationRow = {};
import SearchCondition from '@/components/Personal/SearchCondition.vue';
import Pagination from '@/components/Personal/Pagination.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'',
	components:{
		SearchCondition,Pagination
	},
	data(){
		return{
			tableData,dialogVisible,currentRepresentText,currentOperationRow,
			handleDialogVisible
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
		//点击驳回申诉事件
		entryRejectHandleBtnEvent(){
			this.$confirm('该操作只能进行一次, 是否继续?', '提示', {
			  confirmButtonText: '确定',
			  cancelButtonText: '取消',
			  type: 'warning'
			}).then(() => {
				let data = {};
				data.aid = this.currentOperationRow.aid;
				data.representState = '申诉失败';
				data.agentId = this.$store.getters.employeeId;
				this.submitHanleRepresentInfo(data);
			}).catch(() => {});
		},
		//点击变更正常事件
		entrySuccessHandleBtnEvent(){
			this.$confirm('该操作只能进行一次, 是否继续?', '提示', {
			  confirmButtonText: '确定',
			  cancelButtonText: '取消',
			  type: 'warning'
			}).then(() => {
				let data = {};
				data.aid = this.currentOperationRow.aid;
				data.representState = '申诉通过';
				data.agentId = this.$store.getters.employeeId;
				data.type = '正常';
				this.submitHanleRepresentInfo(data);
			}).catch(() => {});
		},
		//确定申述按钮
		entryRepresentBtnEvent(){
			let data = {};
			data.aid = this.currentOperationRow.aid;
			data.represent = this.currentRepresentText;
			data.representState = '待审核';
			this.submitRepresentInfo(data);
		},
		//点击申诉按钮
		representBtnEvent(row){
			this.currentRepresentText = '';
			this.currentOperationRow = row;
			this.dialogVisible = true;
		},
		//点击处理申述按钮事件
		handleRepresentBtnEvent(row){
			this.currentRepresentText = row.represent;
			this.currentOperationRow = row;
			this.handleDialogVisible = true;
		},
		showTagType(type){
			if(type === 0){
				return '正常';
			}else if(type === 1){
				return '迟到';
			}
			else return '早退';
		},
		tagType(type){
			if(type == '正常') return 'success';
			else return 'warning';
		},
		filterType(value, row) {
			return row.type === value;
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		//提交处理申诉的数据
		submitHanleRepresentInfo(data){
			submitDataForPost("/attendance-record/change",data).then(res=>{
				let result = res.data.data;
				for(let i=0;i<this.tableData.length;i++){
					if(this.tableData[i].aid == data.aid){
						this.tableData[i].representState = result.representState;
						this.tableData[i].agentId = result.agentId;
						this.tableData[i].agentDate = result.agentDate;
						if(data.type == '正常') this.tableData[i].type = result.type;
						break;
					}
				}
				this.handleDialogVisible = false;
				this.$message({
					type: 'success',
					message: '已处理!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '处理过程错误!'
				});
			});
		},
		//提交申诉数据
		submitRepresentInfo(data){
			submitDataForPost("/attendance-record/represent",data).then(res=>{
				let result = res.data.data;
				for(let i=0;i<this.tableData.length;i++){
					if(this.tableData[i].aid == data.aid){
						this.tableData[i].represent = result.represent;
						this.tableData[i].representState = result.representState;
						this.tableData[i].updateDate = result.updateDate;
						break;
					}
				}
				this.dialogVisible = false;
				this.$message({
					type: 'success',
					message: '已申诉，待审核!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '申诉过程错误!'
				});
			});
		},
		//页面打开时拉取所有信息
		async getInitInfo(){
			let params = {
				employeeId:null
			};
			GetForParams("/attendance-record/all",null).then(res=>{
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
@import url("../../../assets/css/Personal/Layout.scss");
</style>
