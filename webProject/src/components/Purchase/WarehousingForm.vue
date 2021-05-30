<template>
	<div id="imquiry-form-main">
		<el-form :inline="true" v-model="inquiryData"  class="demo-form-inline">
			<el-form-item label="供应商:">
			  <el-input readonly v-model="inquiryData.supplierId" placeholder="供应商"></el-input>
			</el-form-item>
			<el-form-item label="关联询价单编号:">
			  <el-input readonly v-model="inquiryData.piId"></el-input>
			</el-form-item>
			<el-form-item label="采购方式:">
				 <el-input readonly v-model="inquiryData.puchaseWay"></el-input>
			</el-form-item>
			<el-form-item label="发票类型:">
				<el-input readonly v-model="inquiryData.invoiceType"></el-input></el-form-item>
			<el-form-item label="结算方式:">
				<el-input readonly v-model="inquiryData.settlementWay"></el-input>
			</el-form-item>
			<el-form-item label="到货方式:">
				<el-input readonly v-model="inquiryData.arrivalWay"></el-input>
			</el-form-item>
		</el-form>
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			
			<el-form-item label="收货仓库:">
			  <el-select v-model="formData.warehouse" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="!seeOrWarehousing" label="主仓库" value="主仓库"></el-option>
			    <el-option :disabled="!seeOrWarehousing" label="原材料库" value="原材料库"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="入库备注:">
			  <el-input :readonly="!seeOrWarehousing" style="width: 650px;" v-model="formData.note" placeholder="备注"></el-input>
			</el-form-item>
			
			<div id="item-input-table">
			<el-table
				:data="tableData"
				stripe
				border
				max-height="300px">
				<el-table-column type="index" :index="indexMethod" width="50"></el-table-column>
				<el-table-column prop="pid" label="物品编码" width="100"></el-table-column>
				<el-table-column prop="name" label="物品名称" width="110"></el-table-column>
				<el-table-column prop="model" label="规格型号" width="80"></el-table-column>
				<el-table-column prop="pic" label="单位" width="50"></el-table-column>
				<el-table-column prop="budgetNum" label="申请数量" width="80"></el-table-column>
				<el-table-column prop="purchasePrice" label="采购单价" width="80"></el-table-column>
				<el-table-column prop="warehousingNum" label="入库数量" width="80">
					<template #default="scope">
						<el-input class="table-el-input"  :readonly="!seeOrWarehousing" v-model="scope.row.warehousingNum"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="warehousingTotal" label="入库金额" width="80">
					<template #default="scope">
						<span v-show="!seeOrWarehousing">{{scope.row.warehousingTotal}}</span>
						<span v-show="seeOrWarehousing" >{{computedWarehousingTotal(scope.$index,scope.row)}}</span>
					</template>
				</el-table-column>
			</el-table>
			</div>
			
			<div class="footer-item">
				<div class="footer-item-left">
					金额合计：{{warehousingSum}}
					<!-- <span v-show="seeOrWarehousing">{{warehousingSum}}</span> -->
					<!-- <span v-show="seeOrWarehousing">{{formData.warehousingTotal}}</span> -->
					元
				</div>
			</div>
		</el-form>
	</div>
</template>

<script>
let formDataTemp = {
	pwId: '',
	poId: '',
	piId: '',
	agentId: '',
	agentDate: '',
	note: '',
	warehouse: '',
	warehousingTotal: '',
	warehousingState: ''
};
let formData = {
	pwId: '',
	poId: '',
	piId: '',
	agentId: '',
	agentDate: '',
	note: '',
	warehouse: '',
	warehousingTotal: '',
	warehousingState: ''
};
let tableData = [];

let inquiryData = {};
let currentWarehousingTotal = -1;	//入库时当前的总价
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'WarehousingForm',
	computed:{
		inputEvent(row){
			console.log(row);
		},
		computedWarehousingTotal(){
			return function(index,row){
				this.tableData[index].warehousingTotal = row.purchasePrice * row.warehousingNum
				return this.tableData[index].warehousingTotal;
			}
		},
		warehousingSum:{
			get(){
				let sum = 0;
				for(let i=0;i<this.tableData.length;i++){
					sum += this.tableData[i].purchasePrice * this.tableData[i].warehousingNum;
				}
				this.currentWarehousingTotal = sum;
				return sum;
			}
		}
	},
	props:{
		seeOrWarehousing:Boolean,
		propsPurchaseInquiry:Object,
		propsFormData:Object
	},
	data(){
		return{
			formData,tableData,inquiryData,currentWarehousingTotal
		}
	},
	methods:{
		indexMethod(index){
			return ++index;
		},
		//根据采购申请单单号拉取清单
		async queryInquiryList(poId){
			let data = {};
			data.poId = poId;
			GetForParams('/purchase-item/current-item-list',data).then(res=>{
				this.tableData = res.data.data;
			}).catch(err=>{
				console.log("失败");
			});
		},
	},
	created(){
	},
	mounted(){
		this.propsPurchaseInquiry.other = '333';
		this.inquiryData = this.propsPurchaseInquiry;
		this.formData = this.propsFormData;
		// console.log(this.inquiryData);
		this.queryInquiryList(this.inquiryData.poId);
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
