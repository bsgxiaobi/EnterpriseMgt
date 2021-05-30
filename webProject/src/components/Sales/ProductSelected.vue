<template>
	<div id="product-selected-main">
		<div>
			<el-form :inline="true" v-model="selectFormData">
				<el-form-item label="选择仓库:">
				  <el-select v-model="selectFormData.warehouseId" placeholder="未选择">
					  <el-option v-for="(item,index) in warehouseData" :key="index" :label="item.warehouseName" :value="item.warehouseId"></el-option>
				  </el-select>
				 </el-form-item>
				  <el-form-item label="物品名称:">
					<el-input v-model="selectFormData.title" placeholder="物品名称"></el-input>
				  </el-form-item>
				<el-button type="primary" size="medium" @click="selectBtnEvent">查询</el-button>
			</el-form>
		</div>
		<el-table id="product-table"
			:data="productTableData"
			stripe
			border
			:header-cell-style="{'text-align':'center'}"
			:cell-style="productTableRowClass"
			@row-dblclick="entrySelectItem"
			title="双击选定"
			height="380">
			<el-table-column prop="iiId" label="编号" width="50"></el-table-column>
			<el-table-column prop="productCode" label="物品编码" width="100"></el-table-column>
			<el-table-column prop="title" label="物品名称" width="150"></el-table-column>
			<!-- <el-table-column prop="shelves.shelvesName" label="所属货架" width="100"></el-table-column> -->
			<el-table-column prop="model" label="规格类型"></el-table-column>
			<el-table-column prop="unit" label="单位"></el-table-column>
			<el-table-column prop="sum" label="单价"></el-table-column>
			<el-table-column prop="price" label="库存"></el-table-column>
			<el-table-column prop="total" label="价值"></el-table-column>
		</el-table>
	</div>
</template>

<script>
let warehouseData = [];
let productTableData = [];
let warehouseId = -1;
let title = '';
let currentSonOperationRow = null;
let selectFormData = {warehouseId:null,title:''};
import {GetForParams,submitDataForPost,getDataForNullParams} from '@/network/RequestApi.js';
export default{
	name:'ProductSelected',
	data(){
		return{
			warehouseId,title,selectFormData,
			warehouseData,productTableData,currentSonOperationRow
		}
	},
	methods:{
		entrySelectItem(row){
			this.currentSonOperationRow = row;
			// console.log("this.currentSonOperationRow",this.currentSonOperationRow);
			this.$parent.$parent.$parent.$parent.enrtySonSelectedEvent();
		},
		selectBtnEvent(){
			this.getProductInfo(this.selectFormData);
		},
		productTableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		//拉取仓库下的物品清单
		async getProductInfo(data){
			GetForParams("/inventory-item/all-params",data).then(res=>{
				this.productTableData = res.data.data;
				// console.log(this.productTableData);
			}).catch(err=>{
				console.log(err);
			});
		},
		//拉取所有仓库信息
		async initWarehouse(){
			getDataForNullParams("/warehouse/all").then(res=>{
				this.warehouseData = res.data.data;
				console.log("this.warehouseData ",this.warehouseData);
				let temp = {warehouseId:-1,warehouseName:'所有仓库'};
				// this.warehouseData.unshift(temp);
			}).catch(err=>{
				console.log(err);
			});
		}
	},
	mounted() {
		this.initWarehouse();
	}
}
</script>

<style>
</style>
