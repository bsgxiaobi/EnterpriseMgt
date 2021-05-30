<template>
	<el-form :inline="true" v-model="fromData"  class="demo-form-inline">
		<el-form-item label="客户单位:">
		  <el-input v-model="fromData.title" placeholder="员工编号"></el-input>
		</el-form-item>
		<el-form-item label="申请人:">
		  <el-input v-model="fromData.title" placeholder="申请人">
			  <template #suffix>
			        <i class="el-icon-search" @click="test()" title="选择申请人"></i>
			      </template>
		  </el-input>
		</el-form-item>
		<el-form-item label="批准人:">
		  <el-input v-model="fromData.title" placeholder="批准人"></el-input>
		</el-form-item>
		<el-form-item label="事由:">
		  <el-input v-model="fromData.title" placeholder="事由"></el-input>
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
	name:'ApplySearchForm',
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
