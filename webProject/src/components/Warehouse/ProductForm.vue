<template>
	<div id="product-form-main">
		<el-form :inline="true" v-model="formData"  class="demo-form-inline">
			<el-form-item label="编码:">
			  <el-input v-model="formData.productCode" placeholder="编码"></el-input>
			</el-form-item>
			<el-form-item label="名称:">
			  <el-input  v-model="formData.title" placeholder="名称"></el-input>
			</el-form-item>
			<div class="abstracts-div">
				<label>介绍：</label>
				<el-input v-model="formData.abstracts" placeholder="介绍"></el-input>
			</div>
			<el-form-item label="规格:">
				<el-input v-model="formData.model" placeholder="规格类型"></el-input>
			</el-form-item>
			<el-form-item label="单位:">
				<el-input v-model="formData.unit" placeholder="单位"></el-input>
			</el-form-item>
			<el-form-item label="数量:">
				<el-input v-model="formData.sum" placeholder="数量"></el-input>
			</el-form-item>
			<el-form-item label="价格:">
				<el-input v-model="formData.price" placeholder="价格"></el-input>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
const formData={
	iiId:null,
	productCode:'',
	warehouseId:'',
	shelvesId:'',
	title:'',
	abstracts:'',
	model:'',
	unit:'',
	sum:null,
	price:null
};
import {GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'ProductForm',
	props:{
		productId:Number,
	},
	data(){
		return{
			formData
		}
	},
	methods:{
		queryFun(iiId){
			this.getOneInventoryItem(iiId);
		},
		//重置信息
		resetInfo(){
			let formDataTemp = {
				iiId:null,productCode:'',warehouseId:'',shelvesId:'',title:'',abstracts:'',
				model:'',unit:'',sum:null,price:null
			};
			this.formData = formDataTemp;
		},
		//拉取单个产品信息
		async getOneInventoryItem(iiId){
			let params = {};
			params.iiId = iiId;
			GetForParams("/inventory-item/one",params).then(res=>{
				this.formData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
	},
	mounted() {
		if(this.productId == -1){
			return;
		}
		this.getOneInventoryItem(this.productId);
	}
}
</script>

<style lang="scss" scoped>
.el-form-item{
	margin-bottom: 20px;
	margin-right: 30px;
}
.el-input{
	// width: 250px;
}
.el-form-item__label{
	// width: 70px;
}
.abstracts-div{
	margin-bottom: 20px;
	.el-input{
		width: 480px;
	}
}
</style>

