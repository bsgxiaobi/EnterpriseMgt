<template>
	<el-form :inline="true" v-model="fromData"  class="demo-form-inline">
		<el-form-item label="销售客户:">
		  <el-input v-model="fromData.title" placeholder="员工编号"></el-input>
		</el-form-item>
		<el-form-item label="经手人:">
		  <el-input v-model="fromData.title" placeholder="申请人">
			  <template #suffix>
			        <i class="el-icon-search" @click="test()" title="选择申请人"></i>
			      </template>
		  </el-input>
		  </el-form-item>
	  <el-form-item label="开单时期:">
	    <el-date-picker
	          v-model="fromData.period"
	          type="daterange"
	          align="right"
	          unlink-panels
	          range-separator="至"
	          start-placeholder="开始日期"
	          end-placeholder="结束日期"
	          :shortcuts="shortcuts"
	    ></el-date-picker>
	  </el-form-item>
	  <el-form-item label="销售方式:">
	    <el-select v-model="fromData.title" class="el-select-sex" placeholder="未指定">
	      <el-option label="限购" value="限购"></el-option>
	      <el-option label="赎购" value="赎购"></el-option>
	    </el-select>
	  </el-form-item>
	  <el-form-item label="发票类型:">
	    <el-select v-model="fromData.title" class="el-select-sex" placeholder="未指定">
	      <el-option label="普通发票" value="普通发票"></el-option>
	      <el-option label="增值税专用发票" value="增值税专用发票"></el-option>
	  	<el-option label="专业发票" value="专业发票"></el-option>
	  	<el-option label="收据" value="收据"></el-option>
	    </el-select>
	  </el-form-item>
	  <el-form-item label="结算方式:">
	    <el-select v-model="fromData.title" class="el-select-sex" placeholder="未指定">
	      <el-option label="现金结算" value="现金结算"></el-option>
	      <el-option label="托收承付" value="托收承付"></el-option>
	    </el-select>
	  </el-form-item>
	  <el-form-item label="交货方式:">
	    <el-select v-model="fromData.title" class="el-select-sex" placeholder="未指定">
	      <el-option label="送货上门" value="送货上门"></el-option>
	      <el-option label="取货" value="取货"></el-option>
	    </el-select>
	  </el-form-item>
	  <el-form-item>
	    <el-button type="primary" @click="">查询</el-button>
	  </el-form-item>
	</el-form>
</template>

<script>
let fromData = {
	title:'',
	period:''
} 
shortcuts: [{
  text: '最近一周',
  value: (() => {
	const end = new Date()
	const start = new Date()
	start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
	return [start, end]
  })(),
}, {
  text: '最近一个月',
  value: (() => {
	const end = new Date()
	const start = new Date()
	start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
	return [start, end]
  })(),
}, {
  text: '最近三个月',
  value: (() => {
	const end = new Date()
	const start = new Date()
	start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
	return [start, end]
  })(),
}];
export default{
	name:'SalesSearchForm',
	data(){
		return{
			fromData
		}
	},
	methods:{
		test(){
			console.log('test-------test');
		}
	}
}
</script>

<style>
</style>
