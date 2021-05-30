<template>
	<div id="attendance-main">
		<el-tabs v-model="activeName" @tab-click="handleClick">
		    
		    <el-tab-pane label="应收单" name="first">
		    	<ReceivableRecord></ReceivableRecord>
		    </el-tab-pane>
			<el-tab-pane label="应付单" name="second">
		    	<PayableRecord></PayableRecord>
		    </el-tab-pane>
		    <el-tab-pane label="应收明细账" name="third">
				<ReceivableLedger></ReceivableLedger>
			</el-tab-pane>
			<el-tab-pane label="应付明细账" name="four">
				<PayableLedger></PayableLedger>
			</el-tab-pane>
		</el-tabs>
		
	</div>
</template>

<script>


import ReceivableRecord from '@/components/Finance/Receivable-Payable/ReceivableRecord.vue';
import PayableRecord from '@/components/Finance/Receivable-Payable/PayableRecord.vue';
import ReceivableLedger from '@/components/Finance/Receivable-Payable/ReceivableLedger.vue';
import PayableLedger from '@/components/Finance/Receivable-Payable/PayableLedger.vue';
export default{
	name:'ReceivablePayable',
	components:{
		ReceivableRecord,PayableRecord,ReceivableLedger,PayableLedger
	},
	data(){
		return{
			activeName:'first',
		}
	}
}
</script>

<style scoped>
@import url("../../assets/css/Personal/Attendance.scss");
</style>
