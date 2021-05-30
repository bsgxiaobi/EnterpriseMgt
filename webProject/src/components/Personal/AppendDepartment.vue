<template>
	<el-form label-position="left" v-model="fromDepartment"  class="demo-form-inline">
		<el-form-item label="部门名称:">
		  <el-input size="medium" v-model="fromDepartment.title" placeholder="部门名称"></el-input>
		</el-form-item>
		<el-form-item label="负责人:">
		  <el-input size="medium" v-model="fromDepartment.administrator" placeholder="负责人"></el-input>
		</el-form-item>
		<el-form-item label="部门描述:">
		  <el-input type="textarea" size="medium" :autosize="{ minRows: 3, maxRows: 6}" v-model="fromDepartment.description" placeholder="部门描述"></el-input>
		</el-form-item>
	</el-form>
</template>

<script>
const fromDepartment={
	title:'',
	administrator:'',
	description:''
};
export default{
	name:'AppendDepartment',
	props:{
		tempModifyData:Object,
	},
	data(){
		return{
			fromDepartment
		}
	}
}
</script>

<style lang="scss" scoped>
.el-form-item{
	margin-bottom: 0px;
}
.el-input{
	// width: 250px;
}
.el-form-item__label{
	// width: 70px;
}
</style>
