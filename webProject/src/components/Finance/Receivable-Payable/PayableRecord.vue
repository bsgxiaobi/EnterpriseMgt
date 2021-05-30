<template>
	<div class="office-disk-main">
		<!-- <SearchForm></SearchForm> -->
		<el-table
			:data="tableData"
			style="width: 100%"
			height="300"
			stripe
			border
			highlight-current-row
			@row-click="customerRowClickEvent"
			:cell-style="contactsTableRowClass">
			<el-table-column fixed prop="sapId" label="单据编号" width="250">
				<template #header>
					<label>单据编号</label>
					<el-button type="text" size="mini" @click="addApplyBtnEvent" icon="el-icon-plus">添加应付单</el-button>
				</template>
			</el-table-column>
			<el-table-column prop="customerInfo.name" label="往来供应商" width="200"></el-table-column>
			<el-table-column prop="abstracts" label="摘要" width="300"></el-table-column>
			<el-table-column prop="agentName" label="经手人"></el-table-column>
			<el-table-column prop="cope" label="应付" width="100"></el-table-column>
			<el-table-column prop="paid" label="已付" width="100"></el-table-column>
			<el-table-column prop="createId" label="制单人" width="150"></el-table-column>
			<el-table-column prop="createDate" label="制单日期" width="200"></el-table-column>
		</el-table>
		
		<el-divider content-position="left">付款单:</el-divider>
		
		<el-table id="contacts-table"
			:data="sonTableData"
			stripe
			border
			:header-cell-style="{'text-align':'center'}"
			:cell-style="contactsTableRowClass"
			height="300">
			<el-table-column fixed prop="spId" label="单据编号" width="200">
				<template #header>
					<label>单据编号</label>
					<el-button type="text" size="mini" @click="addSonApplyBtnEvent" icon="el-icon-plus">添加付款单</el-button>
				</template>
			</el-table-column>s
			<el-table-column prop="type" label="类型" width="200"></el-table-column>
			<el-table-column prop="abstracts" label="摘要" width="300"></el-table-column>
			<el-table-column prop="accountTitle" label="账户名称" width="200"></el-table-column>
			<el-table-column prop="agentName" label="经手人"></el-table-column>
			<el-table-column prop="total" label="金额"></el-table-column>
			<el-table-column prop="createId" label="制单人"></el-table-column>
			<el-table-column prop="createDate" label="制单日期" width="200"></el-table-column>
		</el-table>
		
		<!-- 应付单信息 -->
		<div class="contactsDialogClass">
			<el-dialog
			  title="填写应付单信息"
			  v-model="dialogVisible"
			  width="580px"
			  :before-close="handleClose">
				<el-form :inline="true" v-model="formData"  class="demo-form-inline">
					<el-form-item label="经手人:">
						<el-input v-model="formData.agentName" placeholder="经手人"></el-input>
					</el-form-item>
					<el-form-item label="供应商:">
						<el-input v-model="formData.cid" placeholder="供应商"></el-input>
					</el-form-item>
					<el-form-item label="应付:">
						<el-input v-model="formData.cope" placeholder="应付金额"></el-input>
					</el-form-item>
					<div class="el-item-textarea">
						<label>摘要:</label>
					  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="formData.abstracts" placeholder="摘要"></el-input>
					</div>
				</el-form>
			  <template #footer>
				<span>
			      <el-button @click="dialogVisible = false">取 消</el-button>
				  <el-button type="primary" @click="entryApplyBtnEvent()">确定新增</el-button>
			    </span>
			  </template>
			</el-dialog>
		</div>
		
		<!-- 付款单信息 -->
		<div class="contactsDialogClass">
			<el-dialog
			  title="填写付款单信息"
			  v-model="sonDialogVisible"
			  width="650px"
			  :before-close="handleClose">
			  <el-form :inline="true" v-model="sonFormData"  class="demo-form-inline">
			  	<el-form-item label="付款类型:">
					<el-select v-model="sonFormData.type" placeholder="请选择">
					  <el-option label="采购货物支出" value="采购货物支出"></el-option>
					  <el-option label="预付款" value="预付款"></el-option>
					  <el-option label="其他" value="其他"></el-option>
					</el-select>
				</el-form-item>
			  	<el-form-item label="使用账户:">
			  		<el-select v-model="sonFormData.accountTitle" placeholder="请选择">
			  		  <el-option label="现金" value="现金"></el-option>
			  		  <el-option label="银行账号" value="银行账号"></el-option>
			  		</el-select>
			  	</el-form-item>
			  	<el-form-item label="经手人:">
			  		<el-input v-model="sonFormData.agentName" placeholder="经手人"></el-input>
			  	</el-form-item>
			  	<el-form-item label="本次金额:">
			  		<el-input v-model="sonFormData.total" placeholder="本次金额"></el-input>
			  	</el-form-item>
			  	<div class="el-item-textarea">
			  		<label>摘要:</label>
			  	  <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 6}" size="medium" v-model="sonFormData.abstracts" placeholder="摘要"></el-input>
			  	</div>
			  </el-form>
			  <template #footer>
				<span>
				  <el-button @click="sonDialogVisible = false">取 消</el-button>
				  <el-button type="primary" @click="entrySonApplyBtnEvent()">确定新增</el-button>
				</span>
			  </template>
			</el-dialog>
		</div>
	</div>
</template>

<script>
let tableData = [];
let sonTableData = [];
let dialogVisible = false;
let sonDialogVisible = false;
let currentOperationId = null;//当前操作的客户Id
let formData={cid:null,cope:null,abstracts:'',agent:null,agentName:''};
let sonFormData = {type:'',abstracts:'',accountTitle:'',total:null,agent:null,agentName:'',};
import CustomerInfoForm from '@/components/Customer/CustomerInfoForm.vue';
import ContactsForm from '@/components/Customer/ContactsForm.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'PayableRecord',
	components:{
		CustomerInfoForm,ContactsForm
	},
	data(){
		return{
			tableData,sonTableData,currentOperationId,formData,sonFormData,
			sonDialogVisible,sonDialogVisible,dialogVisible
		}
	},
	methods:{
		contactsTableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		//点击确定添加已付(收)信息的按钮事件
		entrySonApplyBtnEvent(){
			this.$confirm('单据保存后将无法修改，确定输入无误','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '再次核查',
				type: 'warning',
			}).then(_ => {
				console.log("点击确定新增/修改按钮");
				let data = this.sonFormData;
				data.sapId = this.currentOperationId;
				data.agent = 2100003;
				data.createId = this.$store.getters.employeeId;
				this.submitSonRecordData(data);
				// console.log(data);
			}).catch(_ => {});
		},
		//点击添加已付(收)按钮事件
		addSonApplyBtnEvent(){
			if(this.currentOperationId == null){
				this.$message({
					type: 'error',
					message: '请先选择应付(收)单'
				});
				return;
			}
			let sonFormDataTemp = {type:'',abstracts:'',accountTitle:'',total:null,agent:null,agentName:'',};
			this.sonFormData = sonFormDataTemp;
			this.sonDialogVisible = true;
		},
		//应付(收)行点击事件
		customerRowClickEvent(row){
			this.currentOperationId = row.sapId;
			this.getAllSonInfo(row.sapId);
		},
		//点击新增应付(收)按钮事件
		addApplyBtnEvent(){
			let formDataTemp={cid:null,cope:null,abstracts:'',agent:null,agentName:''};
			this.formData = formDataTemp;
			this.dialogVisible = true;
		},
		//点击确定新增应付(收)单按钮
		entryApplyBtnEvent(){
			this.$confirm('单据保存后将无法修改，确定输入无误','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '再次核查',
				type: 'warning',
			}).then(_ => {
				console.log("点击确定新增/修改按钮");
				let data = this.formData;
				data.agent = 2100003;
				data.paid = 0;
				data.createId = this.$store.getters.employeeId;
				console.log(data);
				this.submitRecordData(data);
			}).catch(_ => {});
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
		
		//提交已付(收)数据
		submitSonRecordData(data){
			delete data.createDate;
			submitDataForPost("/single-payment/insert",data).then(res=>{
				let result = res.data.data;
				console.log(result);
				this.sonTableData.unshift(res.data.data);
				this.sonDialogVisible = false;
				//修改应付(收)单中的已付(收)金额
				for(let i=0;i<this.tableData.length;i++){
					if(this.tableData[i].sapId == data.sapId){
						this.tableData[i].paid = Number(this.tableData[i].paid) + Number(data.total);
						break;
					}
				}
				this.$message({
					type: 'success',
					message: '新增已付(收)成功！'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '已付(收)信息操作失败!'
				});
			});
		},
		//提交应付(收)单数据
		submitRecordData(data){
			submitDataForPost("/single-ap/insert",data).then(res=>{
				let result = res.data.data;
				this.tableData.unshift(res.data.data);
				this.dialogVisible = false;
				this.$message({
					type: 'success',
					message: '新增应付(收)成功！'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '应付(收)操作失败!'
				});
			});
		},
		//拉取一条应付(收)单
		async getAllSonInfo(id){
			let params = {};
			params.id = id;
			GetForParams("/single-payment/all",params).then(res=>{
				this.sonTableData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//拉取应付(收)单的所有已付(收)信息
		async getAllSonInfo(id){
			let params = {};
			params.id = id;
			GetForParams("/single-payment/all",params).then(res=>{
				this.sonTableData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//页面打开时拉取所有信息
		getInitInfo(){
			getDataForNullParams("/single-ap/all").then(res=>{
				this.tableData = res.data.data;
				console.log(res.data.data);
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
// @import url("../../assets/css/Public/public.scss");

.contactsDialogClass .el-dialog__body{
	padding:15px;
	height: 200px;
	overflow: auto;
}
</style>
