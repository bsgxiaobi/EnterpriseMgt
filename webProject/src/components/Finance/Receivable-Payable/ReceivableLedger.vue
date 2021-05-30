<template>
	<div class="office-disk-main">
		<!-- <SearchForm></SearchForm> -->
		<div style="margin-bottom: 10px;">
			<el-form :inline="true" v-model="formData"  class="">
				<el-form-item label="开单日期:">
					<el-date-picker
					      v-model="selectDateTemp"
					      type="daterange"
					      range-separator="至"
					      start-placeholder="开始日期"
					      end-placeholder="结束日期">
					</el-date-picker>
				</el-form-item>
				<el-button type="primary" size="medium" @click="selectBtnEvent" icon="el-icon-plus">查询</el-button>
				<el-button type="info" size="medium" @click="selectClearBtnEvent">清空条件</el-button>
				<el-button type="primary" size="medium" @click="selectBtnEvent">汇总账簿</el-button>
			</el-form>
		</div>
		<el-table
			:data="tableData"
			height="500"
			stripe
			border
			highlight-current-row
			:header-cell-style="{'text-align':'center'}"
			:cell-style="tableRowClass">
			<el-table-column :index="indexMethod" label="序号" width="100"></el-table-column>
			<el-table-column prop="sarId" label="单据编号" width="200"></el-table-column>
			<el-table-column prop="abstracts" label="摘要"></el-table-column>
			<el-table-column prop="cid" label="客户编号" width="100"></el-table-column>
			<el-table-column prop="customerInfo.name" label="客户名称"></el-table-column>
			<el-table-column prop="" label="上臂结存" width="100"></el-table-column>
			<el-table-column prop="borrowSum" label="借方" width="100"></el-table-column>
			<el-table-column prop="creditSum" label="贷方" width="100"></el-table-column>
			<el-table-column label="本笔结存" width="100"></el-table-column>
		</el-table>
		<div id="subsidiary-ledger-bottom">
			<!-- <el-form-item label="合计:"></el-form-item> -->
			<label>合计：</label>
			<el-input readonly style="margin-right: 16px;"></el-input>
			<el-input readonly v-model="currentCreditTotal"></el-input>
			<el-input readonly v-model="currentBorrowTotal"></el-input>
			<el-input readonly v-model="currentCreditTotal"></el-input>
			<el-input readonly v-model="currentBorrowTotal"></el-input>
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
	name:'ReceivableLedger',
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
		indexMethod(index){
			return ++index;
		},
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
			this.selectDateTemp = '';
		},
		summaryBtnEvent(){
			let data = {};
			data.startDate = '';
			data.endDate = '';
			this.getAllInfo(data);
		},
		//点击查询
		selectBtnEvent(){
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
				if(data[i].borrowSum != '') this.currentBorrowTotal += Number(data[i].borrowSum);
				if(data[i].creditSum != '') this.currentCreditTotal += Number(data[i].creditSum);
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
			GetForParams("/accounting-subjects/all-general",params).then(res=>{
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
