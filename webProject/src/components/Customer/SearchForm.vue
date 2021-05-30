<template>
	<el-form :inline="true" v-model="fromData"  class="demo-form-inline">
		<el-form-item label="客户单位:">
		  <el-input v-model="fromData.title" placeholder="员工编号"></el-input>
		</el-form-item>
	  <el-form-item label="建立时间:">
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
	  
	  <el-form-item>
	    <el-button type="primary" @click="">查询</el-button>
	  </el-form-item>
	</el-form>
</template>

<script>
let fromData = {
	title:'',
	period:''
}; 
let shortcuts= [{
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
	name:'',
	data(){
		return{
			fromData,shortcuts
		}
	}
}
</script>

<style>
</style>
