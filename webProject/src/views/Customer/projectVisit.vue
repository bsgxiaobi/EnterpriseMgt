<template>
	<div class="office-disk-main">
		<div>
			<el-form :inline="true" v-model="selectFromData"  class="demo-form-inline">
				<el-form-item label="客户单位:">
				  <el-input v-model="selectFromData.title" placeholder=""></el-input>
				</el-form-item>
				<el-form-item label="联系人:">
				  <el-input v-model="selectFromData.name" placeholder=""></el-input>
				</el-form-item>
			  <el-form-item label="建立时间:">
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
			  <el-form-item>
			    <el-button type="primary" @click="selectBtnEvent()" icon="el-icon-search">查询</el-button>
			  </el-form-item>
			   <el-button type="info" @click="selectClearBtnEvent()" icon="el-icon-refresh-right">重置</el-button>
			</el-form>
		</div>
		
		<div style="margin-bottom: 15px;">
			<el-button type="primary" size="medium" @click="addVisitBtnEvent()" icon="el-icon-plus">添加回访记录</el-button>
		</div>
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border>
			<el-table-column fixed prop="customerInfo.name" label="回访客户" width="200"></el-table-column>
			<el-table-column fixed prop="customerContacts.name" label="互访联系人" width="100"></el-table-column>
			<el-table-column fixed prop="type" label="回访方式" width="100"></el-table-column>
			<el-table-column prop="content" label="回访内容">
				<template #default="scope">
					{{scope.row.content.substring(0,13)}}...
				</template>
			</el-table-column>
			<el-table-column prop="summary" label="回访总结">
				<template #default="scope">
					{{scope.row.summary.substring(0,13)}}...
				</template>
			</el-table-column>
			<el-table-column prop="visitDate" label="回访日期" width="200"></el-table-column>
			<el-table-column prop="agent" label="登记人" width="100"></el-table-column>
			<el-table-column prop="agentDate" label="登记日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="120">
				<template #default="scope">
					<el-button
					  @click.native.prevent="detailedBtnEvent(scope.row)"
					  type="success"
					  size="small">
					  查看详情
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<el-dialog
		  :title="DialogTitle"
		  v-model="DialogVisible"
		  width="40%"
		  :before-close="handleClose">
		 <VisitForm ref="VisitFormData" :propVisit="currentOperationVisit"></VisitForm>
		  <template #footer>
			<span>
		      <el-button @click="DialogVisible = false">取 消</el-button>
			  <el-button v-show="currentOperationVisit == null" type="primary" @click="entryVisitBtnEvent()">确定新增</el-button>
		    </span>
		  </template>
		</el-dialog>
		
	</div>
</template>

<script>
let tableData = [];
let DialogVisible = false;
let DialogTitle = '';
let isFristShowDialogVisible = true;
let currentOperationVisit = null;
//=======搜索=======
let selectFromData = {title:'',name:'',startDate:'',endDate:''}; 
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
import SearchForm from '@/components/Customer/SearchForm.vue';
import VisitForm from '@/components/Customer/VisitForm.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'Company',
	components:{
		SearchForm,VisitForm
	},
	data(){
		return{
			selectFromData,selectDateTemp,shortcuts,
			tableData,isFristShowDialogVisible,
			DialogVisible,DialogTitle,currentOperationVisit
		}
	},
	methods:{
		//点击清空
		selectClearBtnEvent(){
			this.selectFromData = {title:'',name:'',startDate:'',endDate:''}; 
			this.selectDateTemp = '';
			this.getInitInfo();
		},
		//点击搜索按钮
		selectBtnEvent(){
			if(this.selectFromData.title == '' && this.selectFromData.name == '' && this.selectDateTemp == ''){
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
			GetForParams("/customer-visit/all-params",params).then(res=>{
				this.tableData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//点击确定新增按钮事件
		entryVisitBtnEvent(){
			let data = this.$refs.VisitFormData.formData;
			data.agent = this.$store.getters.employeeId;
			// console.log("data:",data);
			this.submitVisitData(data);
		},
		//点击新增回访记录按钮事件
		addVisitBtnEvent(){
			this.DialogTitle = '新增回访记录';
			this.currentOperationVisit = null;
			if(!this.isFristShowDialogVisible){
				this.$refs.VisitFormData.resetData();
			}
			this.isFristShowDialogVisible = false; 
			this.DialogVisible = true;
		},
		//点击查看详细按钮事件
		detailedBtnEvent(row){
			this.DialogTitle = '回访记录详情';
			this.currentOperationVisit = row;
			if(!this.isFristShowDialogVisible){
				this.$refs.VisitFormData.formData = row;
			}
			this.isFristShowDialogVisible = false; 
			this.DialogVisible = true;
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		showTagType(type){
			return type === 0?'批准':'否决';
		},
		tagType(type){
			return type === 0?'success':'warning';
		},
		filterType(value, row) {
			return row.type === value;
		},
		//提交回访记录
		submitVisitData(data){
			submitDataForPost("/customer-visit/insert-update",data).then(res=>{
				let result = res.data.data;
				this.tableData.unshift(res.data.data);
				this.DialogVisible = false;
				this.$message({
					type: 'success',
					message: '新增回访记录成功'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '新增回访记录失败!'
				});
			});
		},
		//页面打开时拉取所有信息
		getInitInfo(){
			getDataForNullParams("/customer-visit/all").then(res=>{
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

<style lang="scss">
@import url("../../assets/css/Public/public.scss");

.contactsDialogClass .el-dialog__body{
	padding:15px;
	height: 500px;
	overflow: auto;
}
</style>
