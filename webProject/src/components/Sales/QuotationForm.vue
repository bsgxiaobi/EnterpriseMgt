<template>
	<div id="apply-form-main">
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<el-form-item label="客户单位:">
			  <!-- <el-input :readonly="!isAdd" v-model="formData.customer" placeholder="客户单位"></el-input> -->
			  <el-autocomplete
			        class="inline-input"
			        v-model="suggestedInput"
			        :fetch-suggestions="querySearch"
			        placeholder="请输入内容"
			        @select="handleSelect">
			  	  <template #default="{ item }">
			  		  <span>{{item.name}}</span>
			  		  </template>
			  </el-autocomplete>
			</el-form-item>
			<el-form-item label="销售方式:">
			  <el-select v-model="formData.saleType" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="!isAdd" label="现销" value="现销"></el-option>
			    <el-option :disabled="!isAdd" label="赊销" value="赊销"></el-option>
				<el-option :disabled="!isAdd" label="分期收款" value="分期收款"></el-option>
				<el-option :disabled="!isAdd" label="委托代销" value="委托代销"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="发票类型:">
			  <el-select v-model="formData.invoiceType" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="!isAdd" label="普通发票" value="普通发票"></el-option>
			    <el-option :disabled="!isAdd" label="增值税专用发票" value="增值税专用发票"></el-option>
			    <el-option :disabled="!isAdd" label="专业发票" value="专业发票"></el-option>
			    <el-option :disabled="!isAdd" label="收据" value="收据"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="结算方式:">
			  <el-select v-model="formData.settlementWay" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="!isAdd" label="现金结算" value="现金结算"></el-option>
			    <el-option :disabled="!isAdd" label="托收承付" value="托收承付"></el-option>
			    <el-option :disabled="!isAdd" label="委托收款" value="委托收款"></el-option>
			    <el-option :disabled="!isAdd" label="汇兑" value="汇兑"></el-option>
			    <el-option :disabled="!isAdd" label="信用证" value="信用证"></el-option>
			    <el-option :disabled="!isAdd" label="限额结算" value="限额结算"></el-option>
			    <el-option :disabled="!isAdd" label="转账支票" value="转账支票"></el-option>
			    <el-option :disabled="!isAdd" label="汇款委托书" value="汇款委托书"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="交货方式:">
			  <el-select v-model="formData.arrivalWay" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="!isAdd" label="送货上门" value="送货上门"></el-option>
			    <el-option :disabled="!isAdd" label="取货" value="取货"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="备注:">
			  <el-input style="width: 600px;" :readonly="!isAdd" v-model="formData.note" placeholder="备注"></el-input>
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
						<el-input @dblclick.nav="showSelectedDialog(scope.row)" title="双击选择物品" :readonly="!isAdd" class="table-el-input" v-model="scope.row.pid"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="物品名称" width="100">
					<template #default="scope">
						<el-input @dblclick.nav="showSelectedDialog(scope.row)" title="双击选择物品" class="table-el-input" v-model="scope.row.name"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="规格型号" width="80">
					<template #default="scope">
						<el-input :readonly="!isAdd" class="table-el-input" v-model="scope.row.model"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="单位" width="50">
					<template #default="scope">
						<el-input class="table-el-input" v-model="scope.row.pic"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="单价" width="75">
					<template #default="scope">
						<el-input :readonly="!isAdd" class="table-el-input" v-model="scope.row.price"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="数量" width="75">
					<template #default="scope">
						<el-input :readonly="!isAdd" class="table-el-input" v-model="scope.row.num"></el-input>
					</template>
				</el-table-column>
				<el-table-column label="金额" width="75">
					<template #default="scope">
						<span v-show="isAdd">{{quotationTotal(scope.$index, scope.row)}}</span>
						<span v-show="!isAdd">{{scope.row.total}}</span>
					</template>
				</el-table-column>
				<el-table-column prop="surplusNum" label="账面数量" width="75"></el-table-column>
				<el-table-column label="操作">
					<template #default="scope">
						<el-button size="mini" :disabled="!isAdd" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			</div>
			
			<div class="footer-item">
				<el-button v-show="isAdd" type="primary" @click="addTableItem()" size="medium" icon="el-icon-plus">增加</el-button>
				<div class="footer-item-left">
					金额合计：
					<span v-show="isAdd">{{quotationTotalSum}}</span>
					<span v-show="!isAdd">{{formData.total}}</span>
					元
				</div>
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
	sqId: "",
	customer: "",
	saleType: "",
	invoiceType: "",
	settlementWay: "",
	agent: 0,
	agentDate: "",
	note: "",
	deliveryAgent: "",
	deliveryAgentDate: "",
	deliveryNote: "",
	warehouse: "",
	total: "",
	state:'',
	arrivalWay:''
};
let tableData = [{
	pid:'',
	name:'',
	model:'',
	pic:'',
	price:null,
	num:null,
	total:0,
	surplusNum:null
}
];

let currentQuotationTotal = -1;	//当前总价
let suggestedInput = '';
let suggestedList = [];
let dialogVisible = false;
let currentOperationRow = null;
import {GetForParams,getDataForNullParams} from '@/network/RequestApi.js';
import Subjects from '@/components/Sales/ProductSelected.vue';
export default{
	name:'QuotationForm',
	components:{
		Subjects
	},
	computed:{
		inputEvent(row){
			console.log(row);
		},
		quotationTotal(){
			return function(index,row){
				this.tableData[index].total = row.price * row.num;
				return this.tableData[index].total;
			}
		},
		quotationTotalSum:{
			get(){
				let sum = 0;
				for(let i=0;i<this.tableData.length;i++){
					sum += this.tableData[i].price * this.tableData[i].num;
				}
				this.formData.total = sum;
				return sum;
			}
		},
	},
	props:{
		isAdd:Boolean,
		propsFormData:Object,
		isFristShow:Boolean
	},
	data(){
		return{
			dialogVisible,currentOperationRow,
			suggestedInput,suggestedList,
			formData,tableData,currentQuotationTotal,
		}
	},
	methods:{
		querySearch(queryString, cb){
		    let results = this.suggestedList.filter(item => {
				if (item.name.indexOf(queryString) > -1) {
					return true;
				}
		    });
		    // 调用 callback 返回建议列表的数据
		    cb(results);
		},
		handleSelect(item){
			this.suggestedInput = item.name;
			this.formData.cid = item.cid;
			this.formData.customer = this.suggestedInput;
		},
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
			let tebleDataTemp = {
				pid:'',
				name:'',
				model:'',
				pic:'',
				price:null,
				num:null,
				total:0,
				surplusNum:null
			};
			this.tableData.push(tebleDataTemp);
		},
		queryFun(sqId){
			this.querySalesItemInfo(sqId);
		},
		async getLoadAll(){
			getDataForNullParams("/customer-info/all").then(res=>{
				this.suggestedList = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//根据销售报价单编号拉取销售清单
		querySalesItemInfo(sqId){
			let params = {};
			params.sqId = sqId;
			GetForParams('/sales-quotation/item',params).then(res=>{
				// console.log(res.data);
				this.tableData = res.data.data;
			}).catch(err=>{
				console.log("失败");
			});
		}
	},
	mounted() {
		this.getLoadAll();
		if(this.isAdd){
			// console.log(this.formData);
			this.formData.agent = this.$store.getters.employeeId;
			return;
		}
		//非开具报价单
		console.log(this.propsFormData);
		this.formData = this.propsFormData;
		this.querySalesItemInfo(this.formData.sqId);
	}
}
</script>

<style lang="scss">
/* #item-input-table .el-table td{
	padding: 7px 0px;
}*/
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
