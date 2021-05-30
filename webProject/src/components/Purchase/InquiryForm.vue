<template>
	<div id="imquiry-form-main">
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<el-form-item label="供应商:">
			  <el-input :readonly="isExamine" v-model="formData.supplierId" placeholder="供应商"></el-input>
			</el-form-item>
			<el-form-item label="关联采购单编号:">
			  <el-input readonly v-model="formData.poId"></el-input>
			</el-form-item>
			<el-form-item label="采购方式:">
			  <el-select v-model="formData.puchaseWay" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="isExamine" label="限购" value="限购"></el-option>
			    <el-option :disabled="isExamine" label="赎购" value="赎购"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="发票类型:">
			  <el-select v-model="formData.invoiceType" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="isExamine" label="普通发票" value="普通发票"></el-option>
			    <el-option :disabled="isExamine" label="增值税专用发票" value="增值税专用发票"></el-option>
				<el-option :disabled="isExamine" label="专业发票" value="专业发票"></el-option>
				<el-option :disabled="isExamine" label="收据" value="收据"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="结算方式:">
			  <el-select v-model="formData.settlementWay" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="isExamine" label="现金结算" value="现金结算"></el-option>
			    <el-option :disabled="isExamine" label="托收承付" value="托收承付"></el-option>
				<el-option :disabled="isExamine" label="委托收款" value="委托收款"></el-option>
				<el-option :disabled="isExamine" label="汇兑" value="汇兑"></el-option>
				<el-option :disabled="isExamine" label="信用证" value="信用证"></el-option>
				<el-option :disabled="isExamine" label="限额结算" value="限额结算"></el-option>
				<el-option :disabled="isExamine" label="转账支票" value="转账支票"></el-option>
				<el-option :disabled="isExamine" label="汇款委托书" value="汇款委托书"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="到货方式:">
			  <el-select v-model="formData.arrivalWay" class="el-select-sex" placeholder="未指定">
			    <el-option :disabled="isExamine" label="送货上门" value="送货上门"></el-option>
			    <el-option :disabled="isExamine" label="取货" value="取货"></el-option>
			  </el-select>
			</el-form-item>
			<el-form-item label="备注:">
			  <el-input style="width: 650px;" :readonly="isExamine" v-model="formData.note" placeholder="备注"></el-input>
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
				<el-table-column prop="budgetPrice" label="预算单价" width="80"></el-table-column>
				<el-table-column prop="budgetNum" label="申请数量" width="80"></el-table-column>
				<el-table-column prop="purchasePirce" label="采购单价" width="80">
					<template #default="scope">
						<el-input class="table-el-input" :readonly="isExamine" v-model="scope.row.purchasePrice"></el-input>
					</template>
				</el-table-column>
				<el-table-column prop="purchaseTotal" label="采购金额" width="80">
					<template #default="scope">
						<span v-show="isExamine">{{scope.row.purchaseTotal}}</span>
						<span v-show="!isExamine" >{{computedInquiryTotal(scope.$index,scope.row)}}</span>
					</template>
				</el-table-column>
			</el-table>
			</div>
			
			<div class="footer-item">
				<el-form-item v-show="isExamine" label="审批状态:">
				  <el-select v-model="formData.inquiryState" placeholder="未指定">
				    <el-option label="审核通过" value="审核通过"></el-option>
				    <el-option label="审核驳回" value="审核驳回"></el-option>
					<el-option disabled label="待审核" value="待审核"></el-option>
				  </el-select>
				</el-form-item>
				<div class="footer-item-left">
					金额合计：{{inquiryTotalSum}}元
				</div>
			</div>
		
		</el-form>
	</div>
</template>

<script>
let formDataTemp = {
	piId:'',
	poId:'',
	agentId:'',
	agentDate:'',
	supplierId:'',
	puchaseWay:'',
	invoiceType:'',
	settlementWay:'',
	arrivalWay:'',
	note:'',
	inquiryTotal:-1,
	inquiryState:'',
	purchaseApply:null,
};
let formData = {
	piId:'',
	poId:'',
	agentId:'',
	agentDate:'',
	supplierId:'',
	puchaseWay:'',
	invoiceType:'',
	settlementWay:'',
	arrivalWay:'',
	note:'',
	inquiryTotal:-1,
	inquiryState:'',
	purchaseApply:{
		poId:''
	},
};
let tableData = [];
let currentInquiryTotal = -1;	//当前总价
let currentInquiryState = '';	//当前审核状态
import {GetForParams} from '@/network/RequestApi.js';
export default{
	name:'InquiryForm',
	props:{
		propsInquiryFormData:Object,
		isExamine:Boolean
	},
	computed:{
		inputEvent(row){
			console.log(row);
		},
		computedInquiryTotal(){
			return function(index,row){
				this.tableData[index].purchaseTotal = row.purchasePrice * row.budgetNum
				return this.tableData[index].purchaseTotal;
			}
		},
		inquiryTotalSum:{
			get(){
				let sum = 0;
				for(let i=0;i<this.tableData.length;i++){
					sum += this.tableData[i].purchasePrice * this.tableData[i].budgetNum;
				}
				this.currentInquiryTotal = sum;
				return sum;
			}
		}
	},
	data(){
		return{
			formData,formDataTemp,tableData,currentInquiryTotal
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
		addTableItem(){
			console.log(this.tableData);
			let tebleDataTemp = {
				p_id:'',
				name:'',
				model:'',
				pic:'',
				budget_price:0,
				budget_num:0,
				surplus_num:0,
			};
			this.tableData.push(tebleDataTemp);
		},
		queryFun(data){
			// console.log("传递的data::::::");
			// console.log(data);
			let formDataTemp1 = {
				piId:'',
				poId:'',
				agentId:'',
				agentDate:'',
				supplierId:'',
				puchaseWay:'',
				invoiceType:'',
				settlementWay:'',
				arrivalWay:'',
				note:'',
				inquiryState:'',
				purchaseApply:{
					poId:'',
				},
			};
			formDataTemp1.poId = data.purchaseApply.poId;
			this.formData = formDataTemp1;
			this.formData.agentId = this.$store.getters.employeeId;
			console.log("queryFun----------");
			console.log(this.propsInquiryFormData);
			if(this.propsInquiryFormData.purchaseApply.piId != ''){
				this.queryInquiryInfo(this.propsInquiryFormData.purchaseApply.poId);
			}
			this.queryInquiryList();
		},
		//根据采购申请单单号拉取清单
		async queryInquiryList(){
			let data = {};
			data.poId = this.formData.poId;
			console.log(data);
			GetForParams('/purchase-item/current-item-list',data).then(res=>{
				//console.log("成功返回数据");
				//console.log(res.data);
				this.tableData = res.data.data;
				// console.log(this.tableData);
			}).catch(err=>{
				console.log("失败");
			});
		},
		//根据采购申请单id拉取信息
		queryInquiryInfo(poId){
			let data = {};
			data.poId = poId;
			GetForParams('/purchase-inquiry/one',data).then(res=>{
				console.log("根据采购申请单id拉取信息--成功返回数据");
				console.log(res.data);
				this.formData = res.data.data;
			}).catch(err=>{
				console.log("失败");
			});
		}
	},
	created() {
	},
	mounted() {
		//当第一次显示时
		let formDataTemp2 = {
			piId:'',
			poId:'',
			agentId:'',
			agentDate:'',
			supplierId:'',
			puchaseWay:'',
			invoiceType:'',
			settlementWay:'',
			arrivalWay:'',
			note:'',
			inquiryTotal:-1,
			inquiryState:'',
			purchaseApply:{
				poId:'',
			},
		};
		//formDataTemp2.agentId = this.$store.getters.employeeId;
		//formDataTemp2.purchaseApply.poId = this.propsInquiryFormData.purchaseApply.poId;
		formDataTemp2 = this.propsInquiryFormData;
		this.formData = formDataTemp2;
		//如果有询价单id存在则拉取询价单信息
		console.log("mounted---------");
		console.log(this.propsInquiryFormData);
		if(this.propsInquiryFormData.purchaseApply.piId != ''){
			this.queryInquiryInfo(this.propsInquiryFormData.purchaseApply.poId);
		}
		this.formData.agentId = this.$store.getters.employeeId;
		this.formData.poId = this.propsInquiryFormData.purchaseApply.poId;
		//拉取清单
		// console.log(this.formData);
		this.queryInquiryList();
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
	margin-top: 15px;
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
