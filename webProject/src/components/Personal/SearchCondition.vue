<template>
	<el-form :inline="true" v-model="fromEmployee"  class="demo-form-inline">
		
	  <el-form-item label="员工编号:">
	    <el-input v-model="fromEmployee.id" placeholder="员工编号"></el-input>
	  </el-form-item>
	  <el-form-item label="员工姓名:">
	    <el-input v-model="fromEmployee.name" placeholder="员工姓名"></el-input>
	  </el-form-item>
	  <el-form-item label="性别:">
	    <el-select v-model="fromEmployee.sex" class="el-select-sex" placeholder="性别">
	      <el-option label="男" value="1"></el-option>
	      <el-option label="女" value="0"></el-option>
	    </el-select>
		</el-form-item>
		<el-form-item label="类型:">
		  <el-select v-model="fromEmployee.type" class="el-select-sex" placeholder="类型">
		    <el-option label="奖励" value="1"></el-option>
		    <el-option label="惩罚" value="0"></el-option>
		  </el-select>
	  </el-form-item>
	  <el-form-item label="联系电话:">
	    <el-input v-model="fromEmployee.phone" placeholder="联系电话"></el-input>
	  </el-form-item>
	  <el-form-item label="所属部门:">
	    <el-select v-model="fromEmployee.department" class="el-select-department" placeholder="所属部门">
	      <el-option label="人力资源部" value="1"></el-option>
	      <el-option label="采购部" value="0"></el-option>
		  <el-option label="技术部" value="2"></el-option>
	    </el-select>
	  </el-form-item>
	  <el-form-item>
	    <el-button type="primary" @click="">查询</el-button>
	  </el-form-item>
	</el-form>
</template>

<script>
let fromEmployee = {
	id:'',
	name:'',
	sex:'',
	phone:'',
	department:'',
	academic:'',
	joinDate:'',
	rank:''
}
export default{
	name:'',
	data(){
		return{
			fromEmployee
		}
	}
}
</script>

<style>
</style>
