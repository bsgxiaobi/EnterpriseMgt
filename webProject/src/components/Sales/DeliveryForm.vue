<template>
	<div id="apply-form-main">
		<el-form :inline="true" v-model="fromData"  class="demo-form-inline">
			<el-form-item label="客户单位:">
			  <el-input readonly v-model="formData.customer" placeholder="客户单位"></el-input>
			</el-form-item>
			<el-form-item label="经手员工:">
			  <el-input  v-model="formData.agent" readonly></el-input>
			</el-form-item>
			<el-form-item label="销售方式:">
				<el-input readonly v-model="formData.saleType" placeholder="客户单位"></el-input>
			</el-form-item>
			<el-form-item label="发票类型:">
				<el-input readonly v-model="formData.saleType" placeholder="客户单位"></el-input>
			</el-form-item>
			<el-form-item label="结算方式:">
				<el-input readonly v-model="formData.saleType" placeholder="客户单位"></el-input>
			</el-form-item>
			<el-form-item label="交货方式:">
				<el-input readonly v-model="formData.saleType" placeholder="客户单位"></el-input>
			</el-form-item>
			<el-form-item label="销售备注:">
			  <el-input style="width: 600px;" readonly v-model="formData.note" placeholder="备注"></el-input>
			</el-form-item>
			
			<el-form-item label="发货备注:">
			  <el-input style="width: 600px;" :readonly="!isDelivery" v-model="formData.deliveryNote" placeholder="备注"></el-input>
			</el-form-item>
			
			<div id="item-input-table">
			<el-table
				:data="tableData"
				stripe
				border
				max-height="300px">
				<el-table-column type="index" :index="indexMethod" width="30"></el-table-column>
				<el-table-column prop="pid" label="物品编码" width="75"></el-table-column>
				<el-table-column prop="name" label="物品名称" width="100"></el-table-column>
				<el-table-column prop="model" label="规格型号" width="75"></el-table-column>
				<el-table-column prop="pic" label="单位" width="60"></el-table-column>
				<el-table-column prop="price" label="单价" width="75"></el-table-column>
				<el-table-column prop="num" label="开单数量" width="75"></el-table-column>
				<el-table-column prop="deliveryNum" label="出库数量" width="75">
					<template #default="scope">
						<el-input :readonly="!isDelivery" class="table-el-input" v-model="scope.row.deliveryNum"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="surplusNum" label="库存数量" width="75"></el-table-column>
				<el-table-column label="金额" width="75">
					<template #default="scope">
						{{deliveryTotal(scope.$index, scope.row)}}
					</template>
				</el-table-column>
			</el-table>
			</div>
			
			<div class="footer-item">
				<el-form-item label="发票类型:" v-show="isDelivery">
				  <el-select v-model="formData.warehouse" class="el-select-sex" placeholder="未指定">
				    <el-option label="主仓库" value="主仓库"></el-option>
					<el-option label="原材料库" value="原材料库"></el-option>
					<el-option label="半成品库" value="半成品库"></el-option>
				  </el-select>
				</el-form-item>
				<div class="footer-item-left">
					金额合计：
					<span v-show="isDelivery">{{deliveryTotalSum}}</span>
					<span v-show="!isDelivery">{{formData.deliveryTotal}}</span>
					元
				</div>
			</div>
		
		</el-form>
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
	deliveryTotal:0,
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
	price:0,
	num:0,
	total:0,
	surplusNum:0
}];
let currentDeliveryTotal = -1;	//当前总价
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'DeliveryForm',
	computed:{
		inputEvent(row){
			console.log(row);
		},
		deliveryTotal(){
			return function(index,row){
				return row.price * row.deliveryNum;
			}
		},
		deliveryTotalSum:{
			get(){
				let sum = 0;
				for(let i=0;i<this.tableData.length;i++){
					sum += this.tableData[i].price * this.tableData[i].deliveryNum;
				}
				this.currentDeliveryTotal = sum;
				return sum;
			}
		}
	},
	props:{
		isDelivery:Boolean,
		propsFormData:Object,
		isFristShow:Boolean
	},
	data(){
		return{
			formData,tableData,currentDeliveryTotal
		}
	},
	methods:{
		handleDelete(index, row) {
			console.log(index, row);
			this.tableData.splice(index,1);
		},
		indexMethod(index){
			return ++index;
		},
		queryFun(sqId){
			this.querySalesItemInfo(sqId);
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
		if(this.isFristShow){
			// this.formData.agent = this.$store.getters.employeeId;
			return;
		}
		//非出库
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
