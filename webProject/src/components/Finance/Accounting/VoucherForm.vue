<template>
	<div>
		<div id="voucher-form-header">记&nbsp;账&nbsp;凭&nbsp;证</div>
		<el-form :inline="true" v-model="formData"  class="">
			<div id="voucher-form-top">
				<el-form-item id="form-item-word" label="凭证字:">
				  <el-select size="mini" v-model="formData.voucherWord">
					<el-option label="计" value="计"></el-option>
					<el-option label="收" value="收"></el-option>
					<el-option label="转" value="转"></el-option>
					<el-option label="付" value="付"></el-option>
				  </el-select>
				</el-form-item>
				<el-form-item id="form-item-abstract" label="总摘要:">
					<el-input size="mini" v-model="formData.bvAbstracts" placeholder="总摘要"></el-input>
				</el-form-item>
				<el-form-item id="form-item-num" label="附单据数:">
					<el-input-number size="mini" v-model="formData.attachmentNum" controls-position="right" :min="0" :max="10"></el-input-number>
				</el-form-item>
			</div>
			<div id="voucher-form-table">
			<el-table
				:data="voucherItem"
				style="width: 100%"
				height="320"
				stripe
				border
				:cell-style="tableRowClass">
				<el-table-column label="摘要" width="300">
					<template #default="scope">
						<el-input class="table-el-input" v-model="scope.row.abstracts"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="总科目账">
					<template #default="scope">
						<el-input readonly @dblclick.nav="showSubjectsDialog(scope.row)" title="双击选择会计科目" class="table-el-input" v-model="scope.row.ledger"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="明细科目">
					<template #default="scope">
						<el-input readonly @dblclick.nav="showSubjectsDialog(scope.row)" title="双击选择会计科目" class="table-el-input" v-model="scope.row.detailed"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="借方金额" width="100">
					<template #default="scope">
						<el-input @change="changeBorrow" class="table-el-input table-el-input-num" v-model="scope.row.borrow"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="贷方金额" width="100">
					<template #default="scope">
						<el-input @change="changeCredit" class="table-el-input table-el-input-num" v-model="scope.row.credit"></el-input>
					</template>
				</el-table-column>
			</el-table>
			<el-form-item id="form-item-total" label="合计:"></el-form-item>
			<el-input class="voucher-form-imput-total" readonly size="mini" v-model="currentCreditTotal"></el-input>
			<el-input class="voucher-form-imput-total" readonly size="mini" v-model="currentBorrowTotal"></el-input>
			
			</div>
		</el-form>
		
		<!-- 应付单信息 -->
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
let formData={voucherWord:'记',attachmentNum:0,bvAbstracts:'',total:0};
let voucherItem=[
	{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
	{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
	{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
	{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
	{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
	{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null}
];
let currentOperationRow = null;
let dialogVisible = false;
let currentBorrowTotal = 0;
let currentCreditTotal = 0;
import Subjects from '@/components/Finance/Accounting/Subjects.vue';
export default{
	name:'VoucherForm',
	components:{
		Subjects
	},
	comments:{
		// currentBorrowSum
	},
	props:{
		
	},
	data(){
		return{
			formData,voucherItem,dialogVisible,currentOperationRow,currentBorrowTotal,currentCreditTotal
		}
	},
	methods:{
		changeBorrow(value){
			this.currentBorrowTotal = 0;
			for(let i=0;i<voucherItem.length;i++){
				if(voucherItem[i].borrow != null) this.currentBorrowTotal += Number(voucherItem[i].borrow);
			}
			this.formData.total = this.currentBorrowTotal;
		},
		changeCredit(value){
			this.currentCreditTotal = 0;
			for(let i=0;i<voucherItem.length;i++){
				if(voucherItem[i].credit != null) this.currentCreditTotal += Number(voucherItem[i].credit);
			}
		},
		enrtySonSelectedEvent(){
			let sonData = this.$refs.subjectsForm.currentSonOperationRow;
			// console.log(this.currentOperationRow);
			this.currentOperationRow.asId = sonData.asId;
			this.currentOperationRow.name = sonData.name;
			this.currentOperationRow.ledger = this.currentOperationRow.asId + ' ' + this.currentOperationRow.name;
			this.currentOperationRow.detailed = this.currentOperationRow.asId + ' ' + this.currentOperationRow.name;
			this.dialogVisible = false;
		},
		entrySonBtnEvent(){
			//console.log(this.$refs.subjectsForm.currentSonOperationRow);
			let sonRow = this.$refs.subjectsForm.currentSonOperationRow;
			console.log(sonRow);
		},
		showSubjectsDialog(row){
			this.currentOperationRow = row;
			this.dialogVisible = true;
		},
		resetData(){
			console.log('resetData');
			this.formData={voucherWord:'记',attachmentNum:0,bvAbstracts:'',total:null};
			this.voucherItem=[
				{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
				{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
				{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
				{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
				{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null},
				{abstracts:'',ledger:'',detailed:'',asId:'',name:'',borrow:null,credit:null}
			];
			this.currentBorrowTotal = 0;
			this.currentCreditTotal = 0;
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		
	}
}
</script>

<style lang="scss">
.voucher-form-imput-total{
	float: right;
	width: 100px;
	.el-input__inner{
		text-align: right;
	}
	
}
#voucher-form-header{
	text-align: center;
	font-size: 26px;
	color: #338f64;
	margin-bottom: 10px;
}
#voucher-form-top{
	width: 100%;
}
#voucher-form-top .el-form-item{
	margin-bottom: 5px;
}
#form-item-total{
	margin-bottom: 0px;
}
#form-item-word .el-select{
	width: 70px;
}
#form-item-abstract{
	margin-left: 100px;
}
#form-item-abstract .el-input{
	width: 300px;
}
#form-item-num{
	display: block;
	float: right;
}
#form-item-num .el-input-number{
	width: 90px;
}

#voucher-form-table .el-table td .cell{
	padding: 0 0;
	// text-align: center;
}
#voucher-form-table th .cell{
	text-align: center;
	padding: 0px 0px;
}
#voucher-form-table .el-table__row td{
	padding: 0px 0px;
	height: 45px;
}
// .table-el-input{
// 	height: 40px;
// 	line-height: 40px;
// 	vertical-align: middle;
// 	font-size: 14px;
// }
#voucher-form-table .table-el-input .el-input__inner{
	padding: 2px 5px;
	border:none;
	height: 40px;
	line-height: 40px;
	// text-align: center;
	vertical-align: middle;
	font-size: 14px;
}
#voucher-form-table .table-el-input-num .el-input__inner{
	text-align: right;
}


#voucher-form-table .el-table td,#voucher-form-table .el-table th.is-leaf,#voucher-form-table .el-table--border,#voucher-form-table .el-table--group{
  border-color: #666!important;
}
#voucher-form-table .el-table--border::after,#voucher-form-table .el-table--group::after,#voucher-form-table .el-table::before{
  background-color: #666!important;
}
</style>
