<template>
	<div class="office-disk-main">
		<div>
			<el-form :inline="true" v-model="selectFromData"  class="demo-form-inline">
				<el-form-item label="客户单位:">
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
		
		<div style="margin-bottom: 20px;">
			<el-button type="primary" size="medium" @click="addCustomerBtnEvent()" icon="el-icon-plus">添加客户</el-button>
		</div>
		
		<el-table
			:data="tableData"
			style="width: 100%"
			height="350"
			stripe
			border
			highlight-current-row
			@row-click="customerRowClickEvent">
			<el-table-column fixed prop="name" label="客户单位" width="250"></el-table-column>
			<el-table-column fixed prop="type" label="客户类型" width="100"
				:filters="[{ text: '批准', value: 0 }, { text: '否决', value: 1 }]"
				:filter-method="filterType">
				<template #default="scope">
					<el-tag
					  :type="tagType(scope.row.type)"
					  disable-transitions>{{showTagType(scope.row.type)}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="cid" label="编号" width="75"></el-table-column>
			<el-table-column prop="channel" label="开拓渠道" width="150"></el-table-column>
			<el-table-column prop="address" label="所在地址" width="250"></el-table-column>
			<el-table-column prop="createDate" label="建立日期" width="200"></el-table-column>
			<el-table-column prop="updateDate" label="更新日期" width="200"></el-table-column>
			<el-table-column prop="openBank" label="开户行" width="150"></el-table-column>
			<el-table-column prop="bankAccount" label="银行账户" width="150"></el-table-column>
			<el-table-column prop="note" label="客户介绍" width="300"></el-table-column>
			<el-table-column fixed="right" label="操作" width="120">
				<template #default="scope">
					<el-button
					  @click.native.prevent="modifyCustomerBtnEvnet(scope.row)"
					  type="warning"
					  size="small">
					  更新信息
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-divider content-position="left">客户联系人:</el-divider>
		<el-table id="contacts-table"
			:data="contactsTableData"
			stripe
			border
			:header-cell-style="{'text-align':'center'}"
			:cell-style="contactsTableRowClass"
			height="250">
			<el-table-column fixed prop="name" label="姓名" width="100"></el-table-column>
			<el-table-column fixed prop="position" label="职位" width="100"></el-table-column>
			<el-table-column prop="ccId" label="编号" width="50"></el-table-column>
			<el-table-column prop="department" label="部门" width="100"></el-table-column>
			<el-table-column prop="sex" label="性别"></el-table-column>
			<el-table-column prop="landlineTell" label="固定电话"></el-table-column>
			<el-table-column prop="phoneOne" label="手机号一"></el-table-column>
			<el-table-column prop="phoneTwo" label="手机号二" ></el-table-column>
			<el-table-column prop="email" label="邮箱"></el-table-column>
			<el-table-column prop="qq" label="QQ"></el-table-column>
			<el-table-column prop="wchat" label="微信"></el-table-column>
			<el-table-column prop="createId" label="创建人"></el-table-column>
			<el-table-column prop="updateId" label="更新人"></el-table-column>
			<el-table-column prop="createDate" label="创建日期" width="200"></el-table-column>
			<el-table-column prop="updateDate" label="更新日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="200">
				<template #header>
					<label>操作</label>
					<el-button type="text" size="mini" @click="addContactBtnEvent" icon="el-icon-plus">添加联系人</el-button>
				</template>
				<template #default="scope">
					<el-button
					  @click.native.prevent="modifyContactBtnEvent(scope.row)"
					  type="warning"
					  size="small">
					  更新
					</el-button>
					<el-button
					  @click.native.prevent="deleteContactBtnEvent(scope.row)"
					  type="danger"
					  size="small">
					  删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<!-- 客户信息 -->
		<div class="contactsDialogClass">
			<el-dialog
			  title="填写客户信息"
			  v-model="customerDialogVisible"
			  width="600px"
			  :before-close="handleClose">
				<CustomerInfoForm ref="customerInfoFormData" 
				:propsCustomerFormDataCid="propsCustomerFormDataCid"></CustomerInfoForm>
			  <template #footer>
				<span>
			      <el-button @click="customerDialogVisible = false">取 消</el-button>
				  <el-button type="primary" @click="entryCustomerBtnEvent()">{{entryCustomerBtnTitle}}</el-button>
			    </span>
			  </template>
			</el-dialog>
		</div>
		
		<!-- 联系人信息 -->
		<div class="contactsDialogClass">
			<el-dialog
			  :title="contactsDialogTitle"
			  v-model="contactsDialogVisible"
			  width="46%"
			  :before-close="handleClose">
			  <ContactsForm ref="contactsFormData" :propContactId="currentOperationContactId"></ContactsForm>
			  <template #footer>
				<span>
				  <el-button type="primary" @click="entryContactBtnEvent()">{{contactsDialogBtnTitle}}</el-button>
				  <el-button @click="contactsDialogVisible = false">关 闭</el-button>
				</span>
			  </template>
			</el-dialog>
		</div>
	</div>
</template>

<script>
let tableData = [];
let contactsTableData = [];
let customerDialogVisible = false;
let contactsDialogVisible = false;
let isFristShowCustomerDialog = true;	//是否第一次显示客户信息弹框
let isFristShowContactDialog = true;	//是否第一次显示客户信息弹框
let customerDialogTitle = "";
let contactsDialogTitle = "";
let contactsDialogBtnTitle = '';
let propsCustomerFormDataCid = null;
let isAddCustomer = false;
let entryCustomerBtnTitle = '';//确定客户信息按钮名称
let currentOperationCustomerId = null;//当前操作的客户Id
let currentOperationContactId = null;//当前操作的联系人Id
//=======搜索=======
let selectFromData = {name:'',startDate:'',endDate:''}; 
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
import CustomerInfoForm from '@/components/Customer/CustomerInfoForm.vue';
import ContactsForm from '@/components/Customer/ContactsForm.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'Company',
	components:{
		CustomerInfoForm,ContactsForm
	},
	data(){
		return{
			selectFromData,selectDateTemp,shortcuts,
			tableData,entryCustomerBtnTitle,contactsTableData,currentOperationCustomerId,
			isFristShowCustomerDialog,customerDialogTitle,contactsDialogTitle
			,contactsDialogVisible,contactsDialogVisible,propsCustomerFormDataCid,isAddCustomer,
			customerDialogVisible,contactsDialogBtnTitle,isFristShowContactDialog
		}
	},
	methods:{
		//点击清空
		selectClearBtnEvent(){
			this.selectFromData = {name:'',startDate:'',endDate:''}; 
			this.selectDateTemp = '';
			this.getInitInfo();
		},
		//点击搜索按钮
		selectBtnEvent(){
			if(this.selectFromData.name == '' && this.selectDateTemp == ''){
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
			GetForParams("/customer-info/all-params",params).then(res=>{
				this.tableData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		contactsTableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		//点击联系人信息的确定按钮事件
		entryContactBtnEvent(){
			let data = this.$refs.contactsFormData.formData;
			delete data.updateDate;
			if(data.ccId == null) data.createId = this.$store.getters.employeeId;
			data.updateId = this.$store.getters.employeeId;
			data.cid = this.currentOperationCustomerId;
			console.log(data);
			this.submitContactsData(data,data.createDate);
		},
		//点击更新联系人按钮事件
		modifyContactBtnEvent(row){
			this.contactsDialogBtnTitle = '确定修改';
			this.contactsDialogTitle = '修改联系人信息';
			this.currentOperationContactId = row.ccId;
			if(this.isFristShowContactDialog) this.isFristShowContactDialog = false;
			else this.$refs.contactsFormData.queryFun(row.ccId);
			this.contactsDialogVisible = true;
		},
		//点击删除联系人按钮事件
		deleteContactBtnEvent(row){
			this.$confirm('删除该联系人后将无法找回, 是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				this.deleteContactData(row.ccId);
			}).catch(() => {});
		},
		//点击添加联系人按钮事件
		addContactBtnEvent(){
			if(this.currentOperationCustomerId == null){
				this.$message({
					type: 'error',
					message: '请先选择客户'
				});
				return;
			}
			this.contactsDialogBtnTitle = '确定新增';
			this.contactsDialogTitle = '新增客户联系人';
			this.currentOperationContactId = null;
			if(this.isFristShowContactDialog) this.isFristShowContactDialog = false;
			else this.$refs.contactsFormData.resetInfo();
			this.contactsDialogVisible = true;
		},
		//客户信息行点击事件
		customerRowClickEvent(row){
			this.currentOperationCustomerId = row.cid;
			this.getAllContactsInfo(row.cid);
		},
		//点击新增客户按钮事件
		addCustomerBtnEvent(){
			this.isAddCustomer = true;
			this.customerDialogTitle = "新增客户信息";
			this.entryCustomerBtnTitle = '确定新增';
			if(!this.isFristShowCustomerDialog){
				let propsFormDataTemp = {
					createId: 0,updateId:0,name: "",address: "",openBank: "",bankAccount: "",type: "",
					region: "",note: "",channel: "",updateDate: "",createDate: "",cid: null
				};
				this.$refs.customerInfoFormData.formData = propsFormDataTemp;
			}
			this.isFristShowCustomerDialog = false;
			this.propsCustomerFormDataCid = null;
			this.customerDialogVisible = true;
		},
		//点击修改客户按钮事件
		modifyCustomerBtnEvnet(row){
			this.isAddCustomer = false;
			this.propsCustomerFormDataCid = row.cid;
			this.customerDialogTitle = "修改客户信息";
			this.entryCustomerBtnTitle = '确定新增';
			if(!this.isFristShowCustomerDialog){
				this.$refs.customerInfoFormData.queryFun(this.propsCustomerFormDataCid);	
			}
			this.isFristShowCustomerDialog = false;
			this.customerDialogVisible = true;
		},
		//点击确定客户信息按钮事件
		entryCustomerBtnEvent(){
			let data = this.$refs.customerInfoFormData.formData;
			if(this.isAddCustomer){
				data.createId = this.$store.getters.employeeId;
			}
			data.updateId = this.$store.getters.employeeId;
			delete data.updateDate;
			console.log(data);
			this.submitCustomerData(data,data.createDate);
		},
		//点击确定新增/修改按钮
		entryAddOrModifyEvent(){
			this.$confirm('请谨慎操作。','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				console.log("点击确定新增/修改按钮");
				let data = this.$refs.customerInfoFormData.formData;
				this.submitCustomerData(data);
				console.log(data);
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
		showTagType(type){
			return type === 0?'批准':'否决';
		},
		tagType(type){
			return type === 0?'success':'warning';
		},
		filterType(value, row) {
			return row.type === value;
		},
		//删除联系人请求
		async deleteContactData(ccId){
			let params = {};
			params.ccId = ccId;
			submitDataForPost("/customer-contacts/delete",params).then(res=>{
				if(res.data.data == 'success'){
					for(let i = 0;i<this.contactsTableData.length;i++){
						if(this.contactsTableData[i].ccId == ccId){
							this.contactsTableData.splice(i,1);
							break;
						}
					}
					this.$message({
						type: 'success',
						message: '删除联系人成功!'
					});
				}else{
					this.$message({
						type: 'error',
						message: '删除联系人失败!'
					});
				}
			}).catch(err=>{
				console.log(err);
				this.$message({
					type: 'error',
					message: '删除联系人失败!'
				});
			});
		},
		//拉取客户的所有联系人信息
		async getAllContactsInfo(cid){
			let params = {};
			params.cid = cid;
			GetForParams("/customer-contacts/all",params).then(res=>{
				this.contactsTableData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//提交联系人数据
		submitContactsData(data,createDate){
			delete data.createDate;
			submitDataForPost("/customer-contacts/insert-update",data).then(res=>{
				let result = res.data.data;
				console.log(result);
				let msg = '新增联系人成功！';
				if(data.ccId == null){
					this.contactsTableData.unshift(result);
				}else{
					msg = '更新联系人信息成功！';
					for(let i=0;i<this.contactsTableData.length;i++){
						if(this.contactsTableData[i].ccId == data.ccId){
							console.log("匹配到数据");
							result.createDate = createDate;
							this.contactsTableData[i] = result;
							break;
						}
					}
				}
				this.contactsDialogVisible = false;
				this.$message({
					type: 'success',
					message: msg
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '联系人信息操作失败!'
				});
			});
		},
		//提交客户数据
		submitCustomerData(data,createDate){
			delete data.createDate;
			submitDataForPost("/customer-info/insert-update",data).then(res=>{
				let result = res.data.data;
				let msg = '新增客户信息成功！';
				if(data.cid == null){
					console.log("data.cid == null");
					this.tableData.unshift(result);
				}else{
					msg = '更新客户信息成功！';
					for(let i=0;i<this.tableData.length;i++){
						if(this.tableData[i].cid == data.cid){
							console.log("匹配到数据");
							result.createDate = createDate;
							this.tableData[i] = result;
							break;
						}
					}
				}
				this.customerDialogVisible = false;
				this.$message({
					type: 'success',
					message: msg
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '客户信息操作失败!'
				});
			});
		},
		//页面打开时拉取所有信息
		getInitInfo(){
			getDataForNullParams("/customer-info/all").then(res=>{
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
	height: 460px;
	overflow: auto;
}
</style>
