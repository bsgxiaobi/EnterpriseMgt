<template>
	<div class="office-disk-main">
		<!-- <SearchForm></SearchForm> -->
		<div style="margin-bottom: 10px;">
			<el-form :inline="true" v-model="formData"  class="">
				<el-form-item label="凭证日期:">
					<el-date-picker
					      v-model="selectDateTemp"
					      type="daterange"
					      range-separator="至"
					      start-placeholder="开始日期"
					      end-placeholder="结束日期">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="明细科目:">
					<el-input 
						readonly
						@dblclick.nav="showSubjectsDialog()" 
						v-model="formData.name" 
						title="双击选择会计科目"
						placeholder="双击选择会计科目"></el-input>
				</el-form-item>
				
				<el-button type="primary" size="medium" @click="selectBtnEvent" icon="el-icon-plus">查询</el-button>
				<el-button type="info" size="medium" @click="selectClearBtnEvent">清空</el-button>
			</el-form>
		</div>
		<el-table
			:data="tableData"
			style="width: 100%"
			height="500"
			stripe
			border
			highlight-current-row
			:header-cell-style="{'text-align':'center'}"
			:cell-style="tableRowClass">
			<el-table-column prop="viId" label="编号" width="100"></el-table-column>
			<el-table-column prop="abstracts" label="摘要"></el-table-column>
			<el-table-column prop="ledger" label="总账科目"></el-table-column>
			<el-table-column prop="detailed" label="明细科目"></el-table-column>
			 <el-table-column
			      prop="tag"
			      label="方向"
			      width="100"
			      :filters="[{ text: '借', value: '借' }, { text: '贷', value: '贷' }]"
			      :filter-method="filterTag">
			      <template #default="scope">
			        <el-tag
			          :type="scope.row.borrow != '' ? 'primary' : 'success'"
			          disable-transitions>{{scope.row.borrow != ''?'借':'贷'}}</el-tag>
			      </template>
			    </el-table-column>
			<el-table-column prop="borrow" label="借方" width="100"></el-table-column>
			<el-table-column prop="credit" label="贷方" width="100"></el-table-column>
		</el-table>
		<div id="subsidiary-ledger-bottom">
			<!-- <el-form-item label="合计:"></el-form-item> -->
			<label>合计：</label>
			<el-input readonly v-model="currentCreditTotal"></el-input>
			<el-input readonly v-model="currentBorrowTotal"></el-input>
		</div>
		<div id="subjects-dialog-view">
			<el-dialog
			  title="选择会计科目"
			  v-model="dialogVisible"
			  width="500px">
			  <Subjects ref="subjectsForm"></Subjects>
			  <!-- <template #footer>
				<span>
			      <el-button @click="dialogVisible = false">关闭</el-button>
				  <el-button type="primary" @click="entrySonBtnEvent()">确定</el-button>
			    </span>
			  </template> -->
			</el-dialog>
		</div>
	</div>
</template>

<script>
let tableData = [];
let sonTableData = [];
let dialogVisible = false;
let isFristShowDialog = true;
let currentOperationId = null;//当前操作的Id
let currentSelectedItem = [];
let formData={asId:null,name:'',startDate:'',endDate:''};
let selectDateTemp= '';
let currentBorrowTotal = 0;
let currentCreditTotal = 0;
import Subjects from '@/components/Finance/Accounting/Subjects.vue';
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'SubsidiaryLedger',
	components:{
		Subjects
	},
	data(){
		return{
			tableData,sonTableData,currentOperationId,formData,
			dialogVisible,isFristShowDialog,currentSelectedItem,selectDateTemp,
			currentBorrowTotal,currentCreditTotal
		}
	},
	methods:{
		showSubjectsDialog(){
			this.dialogVisible = true;
		},
		enrtySonSelectedEvent(){
			let sonData = this.$refs.subjectsForm.currentSonOperationRow;
			this.formData.asId = sonData.asId;
			this.formData.name = sonData.asId + ' ' + sonData.name;
			this.dialogVisible = false;
		},
		tableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		//点击清空
		selectClearBtnEvent(){
			this.formData={asId:null,name:'',startDate:'',endDate:'',dateTemp:''};
			this.selectDateTemp = '';
		},
		//点击查询
		selectBtnEvent(){
			if(this.formData.name == '' && this.selectDateTemp == ''){
				this.$message({
					type: 'error',
					message: '请选择查询条件'
				});
				return;
			}
			//处理选择的日期
			if(this.selectDateTemp != ''){
				this.formData.startDate = this.dateToString(this.selectDateTemp[0]) + ' 00:00:00';
				this.formData.endDate = this.dateToString(this.selectDateTemp[1]) + ' 23:59:59';
			}
			// let str2 = JSON.stringify(this.selectDateTemp[1]);
			// this.formData.endDate = str2.substr(1,10) + ' 23:59:59';
			// console.log(this.formData);
			this.getAllInfo(this.formData);
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
		totalSumFun(data){
			this.currentBorrowTotal = 0;
			this.currentCreditTotal = 0;
			for(let i = 0;i<data.length;i++){
				if(data[i].borrow != '') this.currentBorrowTotal += Number(data[i].borrow);
				if(data[i].credit != '') this.currentCreditTotal += Number(data[i].credit);
			}
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
		//根据条件拉取所有科目明细账信息
		async getAllInfo(params){
			GetForParams("/voucher-item/all-params",params).then(res=>{
				this.tableData = res.data.data;
				this.totalSumFun(res.data.data);
				// console.log(res.data.data);
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		// this.getInitInfo();
	},
}
</script>

<style lang="scss">
// @import url("../../assets/css/Public/public.scss");

#subjects-dialog-view .el-dialog__body{
	padding:0px 15px;
	// height: 400px;
	overflow: auto;
}
#subjects-dialog-view .el-dialog__header{
	padding-bottom: 0px;
}
#subjects-dialog-view .el-dialog__footer{
	padding-top: 0px;
}
#subsidiary-ledger-bottom{
	border-bottom: 1px #ddd solid;
	border-left: 1px #ddd solid;
	border-right: 1px #ddd solid;
	height: 40px;
	line-height: 40px;
	.el-input{
		float: right;
		width: 100px;
		.el-input__inner{
			text-align: right;
		}
	}
}
</style>
