<template>
	<div class="office-disk-main">
		
		<div>
			<!-- <el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="claimDialogVisible = true" icon="el-icon-plus">申请支领</el-button>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="claimDialogVisible = true" icon="el-icon-plus">物品发放</el-button> -->
		</div>
		
		<el-table
			:data="tableData"
			style="width: 100%"
			stripe
			border
			class="el-table-datail">
			<el-table-column fixed prop="il_id" label="单据编号" width="150"></el-table-column>
			<el-table-column fixed prop="warehouse" label="仓库名称" width="150"></el-table-column>
			<el-table-column prop="agent_date" label="记账日期" width="150"></el-table-column>
			<el-table-column prop="abstract" label="摘要" width="300"></el-table-column>
			<el-table-column prop="item_id" label="物品编码" width="100"></el-table-column>
			<el-table-column prop="item_name" label="物品名称" width="100"></el-table-column>
			<el-table-column prop="item_model" label="规格型号" width="100"></el-table-column>
			<el-table-column prop="item_unit" label="单位" width="75"></el-table-column>
			<el-table-column prop="last_sum" label="上笔结存" width="100"></el-table-column>
			<el-table-column label="借方">
				<el-table-column prop="borrower_price" label="单价" width="75"></el-table-column>
				<el-table-column prop="borrower_num" label="数量" width="75"></el-table-column>
				<el-table-column prop="borrower_total" label="金额" width="100"></el-table-column>
			</el-table-column>
			<el-table-column label="贷方">
				<el-table-column prop="lender_price" label="单价" width="75"></el-table-column>
				<el-table-column prop="lender_num" label="数量" width="75"></el-table-column>
				<el-table-column prop="lender_total" label="金额" width="100"></el-table-column>
			</el-table-column>
			<el-table-column prop="sum" label="本笔结存" width="100"></el-table-column>
			<el-table-column prop="customer" label="客户单位" width="250"></el-table-column>
			<el-table-column prop="supplier" label="供应商" width="250"></el-table-column>
			<el-table-column prop="agent" label="经手人" width="100"></el-table-column>
		</el-table>
		
	</div>
</template>

<script>
let tableData = [{
	il_id:'',
	warehouse:'',
	abstract:'',
	item_id:'',
	item_name:'',
	item_model:'',
	item_unit:'',
	last_sum:'',
	borrower_num:'',
	borrower_price:'',
	borrower_total:'',
	lender_num:'',
	lender_price:'',
	lender_total:'',
	sum:'',
	customer:'',
	supplier:'',
	agent:'',
	agent_data:'',
},
];
let claimDialogVisible = false;
let returnDialogVisible = false;
import ClaimForm from '@/components/Warehouse/ClaimForm.vue';
import ReturnForm from '@/components/Warehouse/ReturnForm.vue';
export default{
	name:'Detail',
	components:{
		ClaimForm,ReturnForm
	},
	data(){
		return{
			tableData,claimDialogVisible,returnDialogVisible
		}
	},
	methods:{
	}
}
</script>

<style lang="scss">
@import url("../../assets/css/Public/public.scss");

.contactsDialogClass .el-dialog__body{
	padding:15px 15px 0px 15px;
	height: 470px;
	overflow: auto;
}	
.el-table-datail th{
	text-align: center;
	padding:5px 0px;
}
</style>
