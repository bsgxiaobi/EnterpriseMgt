<template>
	<div id="apply-form-main">
		<el-form :inline="true" v-model="formData" id="pruchase-apply-form">
			<!-- <el-form-item label="申请人:">
				<label class="label-form-data">{{formData.employeeId}}</label>
			</el-form-item>
			<el-form-item label="单号:" v-show="!isApplyOperation">
				 <label class="label-form-data">{{formData.poId}}</label>
			</el-form-item>
			<el-form-item label="申请日期:" v-show="!isApplyOperation">
				<label class="label-form-data">{{formData.applyDate}}</label>
			</el-form-item> -->
			<el-form-item label="事由:">
			  <el-input style="width: 650px;" v-model="formData.cause" placeholder="事由"></el-input>
			</el-form-item>
			
			<div id="item-input-table">
			<el-table
				:data="tableData"
				stripe
				border
				max-height="300px">
				<el-table-column type="index" :index="indexMethod" width="30"></el-table-column>
				<el-table-column label="物品编码" width="80">
					<template #default="scope">
						<el-input @dblclick.nav="showSelectedDialog(scope.row)" class="table-el-input" :readonly="isApplyOperation?false:'readonly'" v-model="scope.row.pid"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="" label="物品名称" width="100">
					<template #default="scope">
						<el-input @dblclick.nav="showSelectedDialog(scope.row)" class="table-el-input" :readonly="isApplyOperation?false:'readonly'" v-model="scope.row.name"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="" label="规格型号" width="80">
					<template #default="scope">
						<el-input class="table-el-input" :readonly="isApplyOperation?false:'readonly'" v-model="scope.row.model"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="" label="单位" width="50">
					<template #default="scope">
						<el-input class="table-el-input" :readonly="isApplyOperation?false:'readonly'" v-model="scope.row.pic"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="name" label="预算单价" width="75">
					<template #default="scope">
						<el-input class="table-el-input" :readonly="isApplyOperation?false:'readonly'" v-model="scope.row.budgetPrice"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="name" label="申请数量" width="75">
					<template #default="scope">
						<el-input class="table-el-input" :readonly="isApplyOperation?false:'readonly'" v-model="scope.row.budgetNum"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="budgetTotal" label="预算金额" width="75">
					<template #default="scope">
						<!-- {{scope.row.budget_total}} -->
						<span v-show="!isApplyOperation">{{scope.row.budgetTotal}}</span>
						<span v-show="isApplyOperation" >{{computedBudgetTotal(scope.$index,scope.row)}}</span>
						
						<!-- <div style="width: 100%;height: 40px;line-height: 40px;text-align: center;">{{budgetTotal(scope.row)}}</div> -->
					</template>
				</el-table-column>
				<el-table-column prop="name" label="账面数量" width="75">
					<template #default="scope">
						<el-input class="table-el-input" :readonly="isApplyOperation?false:'readonly'" v-model="scope.row.surplusNum"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="name" label="操作">
					<template #default="scope">
						<el-button v-show="isApplyOperation" size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			</div>
			
			<div class="footer-item">
				<el-button type="primary" v-show="isApplyOperation" @click="addTableItem()" size="medium" icon="el-icon-plus">增加</el-button>
				<div class="footer-item-left">
					金额合计：{{budgetTotalSum}}元
				</div>
				<el-form-item label="审核状态:" v-show="!isApplyOperation">
				  <el-select v-model="formData.state" class="apply-form-select" placeholder="未指定">
					<el-option label="待定" value="待定"></el-option>
				    <el-option label="批准" value="批准"></el-option>
					<el-option label="否决" value="否决"></el-option>
				  </el-select>
				</el-form-item>
			</div>
		
		</el-form>
		
		<div id="subjects-dialog-view">
			<el-dialog
			  title="选择物品"
			  v-model="dialogVisible"
			  width="800px">
			  <Subjects ref="subjectsForm"></Subjects>
			</el-dialog>
		</div>
	</div>
</template>

<script>
let formData = {
	poId:'',
	employeeId:null,
	state:'待定',
	cause:'',
	applyDate:'',
	approver:'',
	approvalDate:'',
};
let formDataTemp = {
	poId:'',
	employeeId:null,
	state:'待定',
	cause:'',
	applyDate:'',
	approver:'',
	approvalDate:'',
};
let tebleDataTemp = {
	itemId:null,
	poId:'',
	pid:null,
	name:'',
	model:'',
	pic:'',
	budgetPrice:null,
	budgetNum:null,
	budgetTotal:0,
	surplusNum:null,
};
let tableData = [{
	itemId:null,
	poId:'',
	pid:null,
	name:'',
	model:'',
	pic:'',
	budgetPrice:null,
	budgetNum:null,
	budgetTotal:0,
	surplusNum:null,
}
];
let suggestedInput = '';
let suggestedList = [];
let dialogVisible = false;
let currentOperationRow = null;
import {GetForParams,getDataForNullParams} from '@/network/RequestApi.js';
import Subjects from '@/components/Sales/ProductSelected.vue';
export default{
	name:'ApplyForm',
	components:{
		Subjects
	},
	props:{
		selectedApplyData:Object,
		isApplyOperation:Boolean,
	},
	computed:{
		inputEvent(row){
			console.log(row);
		},
		computedBudgetTotal(){
			return function(index,row){
				this.tableData[index].budgetTotal = row.budgetPrice * row.budgetNum
				return this.tableData[index].budgetTotal;
			}
		},
		budgetTotalSum:{
			get(){
				let sum = 0;
				for(let i=0;i<this.tableData.length;i++){
					sum += this.tableData[i].budgetPrice * this.tableData[i].budgetNum;
				}
				return sum;
			}
		}
	},
	data(){
		return{
			dialogVisible,currentOperationRow,
			suggestedInput,suggestedList,
			formData,tableData,tebleDataTemp,formDataTemp
		}
	},
	methods:{
		//双击显示
		showSelectedDialog(row){
			// console.log("row",row);
			this.currentOperationRow = row;
			this.dialogVisible = true;
		},
		enrtySonSelectedEvent(){
			let sonData = this.$refs.subjectsForm.currentSonOperationRow;
			// console.log("sonData",sonData);
			this.currentOperationRow.pid = sonData.productCode;
			this.currentOperationRow.model = sonData.model;
			this.currentOperationRow.name = sonData.title;
			this.currentOperationRow.pic = sonData.unit;
			this.currentOperationRow.price = sonData.price;
			this.currentOperationRow.surplusNum = sonData.sum;
			this.dialogVisible = false;
		},
		handleDelete(index, row) {
			console.log(index, row);
			this.tableData.splice(index,1);
		},
		indexMethod(index){
			return ++index;
		},
		addTableItem(){
			console.log(this.tableData);
			let tebleDataTempNew = {
				itemId:null,
				poId:'',
				pid:null,
				name:'',
				model:'',
				pic:'',
				budgetPrice:null,
				budgetNum:null,
				budgetTotal:0,
				surplusNum:null,
			};
			this.tableData.push(tebleDataTempNew);
		},
		queryFun(data){
			this.formData = data;
			this.queryApplyList();
		},
		//根据单据编号拉去清单列表
		async queryApplyList(){
			let data = {
				poId:''
			};
			data.poId = this.formData.poId;
			console.log(data);
			GetForParams('/purchase-item/current-item-list',data).then(res=>{
				//console.log("成功返回数据");
				//console.log(res.data);
				this.tableData = res.data.data;
				console.log(this.tableData);
			}).catch(err=>{
				console.log("失败");
			});
		}
	},
	mounted(){
		if(!this.isApplyOperation){
			console.log(this.formData);
			this.queryApplyList();
		}
	},
	created() {
		//当第一次显示时填值
		console.log("this.selectedApplyData::::");
		console.log(this.selectedApplyData);
		this.formDataTemp.employeeId = this.$store.getters.employeeId;
		//当为点击申请按钮时
		if(this.isApplyOperation == true){
			this.formData.employeeId = this.$store.getters.employeeId;
		}else{
			this.formData = this.selectedApplyData;
		}
		// console.log(this.formData);
	},
	watch:{
		// selectedApplyData:function(newVal,oldVal){
		// 	console.log(newVal);
		// 	this.formData = newVal;
		// 	this.queryApplyList();
		// }
	},
}
</script>

<style lang="scss">
/* #item-input-table .el-table td{
	padding: 7px 0px;
}*/
.apply-form-select .el-input__inner{
	width: 120px;
}
#pruchase-apply-form .pruchase-apply-input-short{
	width: 150px;
}
#pruchase-apply-form .label-form-data{
	font-size: 18px;
	padding-right: 20px;
}
#item-input-table .el-table td .cell{
	padding: 0 0;
	text-align: center;
}
#item-input-table th .cell{
	text-align: center;
	padding: 0px 0px;
}
#item-input-table .el-table__row td{
	padding: 5px 0px;
}
.table-el-input{
	height: 30px;
	line-height: 30px;
	vertical-align: middle;
	font-size: 14px;
}
.table-el-input .el-input__inner{
	padding: 0px 0px;
	border:none;
	height: 30px;
	line-height: 30px;
	text-align: center;
	vertical-align: middle;
	font-size: 14px;
}
.footer-item{
	margin-top: 5px;
}
.footer-item-left{
	float: right;
	width: 200px;
	line-height: 36px;
	height: 36px;
	font-size: 16px;
	text-align: right;
}
</style>
