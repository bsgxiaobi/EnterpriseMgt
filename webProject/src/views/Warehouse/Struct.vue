<template>
	<div class="office-disk-main" id="warehouse-shelves-info">
		<div>
			<label class="warehouse-select-item">选择仓库:
			  <el-select @change="changeWarehouse()" v-model="currentWarehouseId" placeholder="未选择">
			    <el-option v-for="(item,index) in warehouseData" :key="index" :label="item.warehouseName" :value="item.warehouseId"></el-option>
			  </el-select>
			</label>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="addWarehouseBtnEvent" icon="el-icon-plus">添加仓库</el-button>
			<el-button style="margin-bottom: 20px;" type="primary" size="medium" @click="modifyWarehouseBtnEvent">修改当前仓库</el-button>
		</div>
		<el-table id="shelves-table"
			:data="tableData"
			stripe
			border
			height="350"
			highlight-current-row
			@row-click="shelvesRowClickEvent"
			:cell-style="tableRowClass"
			row-key="shelvesId"
			:tree-props="{children: 'shelvesList'}">
			<el-table-column fixed prop="shelvesName" label="货架号">
				<template #header>
					<label>货架</label>
					<el-button type="text" size="mini" @click="addRootShelvesBtnEvent" icon="el-icon-plus">添加</el-button>
				</template>
			</el-table-column>
			<el-table-column prop="note" label="摘要" ></el-table-column>
			<el-table-column prop="agentId" label="经手人" width="100"></el-table-column>
			<el-table-column prop="updateDate" label="更新日期" width="200"></el-table-column>
			<el-table-column fixed="right" label="操作" width="220">
				<template #default="scope">
					<el-button
					  @click.native.prevent="addShelvesBtnEvent(scope.row)"
					  type="success"
					  size="small">
					  添加
					</el-button>
					<el-button
					  @click.native.prevent="modifyShelvesBtnEvent(scope.row)"
					  type="warning"
					  size="small">
					  修改
					</el-button>
					<el-button
					  @click.native.prevent="deleteShelvesBtnEvent(scope.row)"
					  type="danger"
					  size="small">
					  删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-divider content-position="left">选中货架下的所有物品:</el-divider>
		<el-table id="product-table"
			:data="productTableData"
			stripe
			border
			:header-cell-style="{'text-align':'center'}"
			:cell-style="productTableRowClass"
			height="300">
			<el-table-column fixed prop="iiId" label="编号" width="50"></el-table-column>
			<el-table-column fixed prop="productCode" label="物品编码" width="100"></el-table-column>
			<el-table-column fixed prop="title" label="物品名称" width="150"></el-table-column>
			<el-table-column prop="warehouse.warehouseName" label="所属仓库"></el-table-column>
			<el-table-column prop="shelves.shelvesName" label="所属货架" width="100"></el-table-column>
			<el-table-column prop="abstracts" label="物品描述" width="200"></el-table-column>
			<el-table-column prop="model" label="规格类型" ></el-table-column>
			<el-table-column prop="unit" label="单位"></el-table-column>
			<el-table-column fixed="right" prop="sum" label="单价"></el-table-column>
			<el-table-column fixed="right" prop="price" label="库存"></el-table-column>
			<el-table-column fixed="right" prop="total" label="价值"></el-table-column>
			<el-table-column fixed="right" label="操作" width="200">
				<template #header>
					<label>操作</label>
					<el-button type="text" size="mini" @click="addProductBtnEvent" icon="el-icon-plus">添加物品</el-button>
				</template>
				<template #default="scope">
					<el-button
					  @click.native.prevent="modifyProductBtnEvent(scope.row)"
					  type="warning"
					  size="small">
					  盘点更新
					</el-button>
					<el-button
					  @click.native.prevent="deleteInventoryItemBtnEvent(scope.row)"
					  type="danger"
					  size="small">
					  删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<el-dialog
		  class="dialogClass"
		  :title="warehouseDialogTitle"
		  v-model="warehouseDialogVisible"
		  width="40%"
		  :before-close="handleClose">
		  <el-form label-position="left" v-model="warehouseFormData"  class="demo-form-inline">
		  	<el-form-item label="仓库名称:">
		  	  <el-input size="medium" v-model="warehouseFormData.warehouseName" placeholder="填写仓库名称"></el-input>
		  	</el-form-item>
		  	<el-form-item label="描述:">
		  	  <el-input type="textarea" size="medium" :autosize="{ minRows: 3, maxRows: 6}" v-model="warehouseFormData.introduce" placeholder="描述仓库"></el-input>
		  	</el-form-item>
		  </el-form>
		  <template #footer>
			<span>
		      <el-button @click="warehouseDialogVisible = false">取 消</el-button>
			  <el-button type="primary" @click="entryAddOrUpdateWarehouseEvent()">{{warehouseDialogBtnTitle}}</el-button>
		    </span>
		  </template>
		</el-dialog>
		
		<el-dialog
		  :title="shelvesDialogTitle"
		  v-model="shelvesDialogVisible"
		  width="40%"
		  :before-close="handleClose">
		  <el-form label-position="left" v-model="shelvesFormData"  class="demo-form-inline">
		  	<el-form-item label="货架编号:">
		  	  <el-input size="medium" v-model="shelvesFormData.shelvesName" placeholder="货架编号"></el-input>
		  	</el-form-item>
		  	<el-form-item label="货架描述:">
		  	  <el-input type="textarea" size="medium" :autosize="{ minRows: 3, maxRows: 6}" v-model="shelvesFormData.note" placeholder="货架描述"></el-input>
		  	</el-form-item>
		  </el-form>
		  <template #footer>
			<span>
		      <el-button @click="shelvesDialogVisible = false">取 消</el-button>
			  <el-button type="primary" @click="entrySumbitShelvesEvent()">确定提交</el-button>
		    </span>
		  </template>
		</el-dialog>
		
		<el-dialog
		  class="dialogClass"
		  :title="productDialogTitle"
		  v-model="productDialogVisible"
		  width="600px"
		  :before-close="handleClose">
		  <ProductForm ref="productFormData" :productId="currentOperationProductId"></ProductForm>
		  <template #footer>
			<span>
		      <el-button @click="productDialogVisible = false">取 消</el-button>
			  <el-button type="primary" @click="entryAddOrUpdateProductEvent()">{{productDialogBtnTitle}}</el-button>
		    </span>
		  </template>
		</el-dialog>
	</div>
</template>

<script>
let tableData = [];	//货架表
let productTableData = [];//物品表
const warehouseData = [];//仓库表
let warehouseFormData = {
	warehouseId:null,
	warehouseName:'',
	updateDate:null,
	agentId:null,
	introduce:''
};
let shelvesFormData = {};
let warehouseDialogTitle = '';
let warehouseDialogVisible = false;
let shelvesDialogTitle = '';
let shelvesDialogVisible = false;
let currentWarehouseId = '';
let currentWarehouseIndex = 0;
let warehouseDialogBtnTitle = '';
let productDialogVisible = false;
let productDialogTitle = '';
let productDialogBtnTitle = '';
let currentOperationShelvesRow = {};
let currentShelvesOperation = 0;//当前货架操作方式,0修改，1新增子货架
let currentOperationProductId = -1;
let isFristShowProductForm = true;
import ProductForm from '@/components/Warehouse/ProductForm.vue';
import {getDataForNullParams,GetForParams,submitDataForPost} from '@/network/RequestApi.js';
export default{
	name:'Struct',
	components:{
		ProductForm
	},
	data(){
		return{
			warehouseData,warehouseFormData,tableData,warehouseDialogTitle,warehouseDialogBtnTitle,
			warehouseDialogVisible,shelvesDialogVisible,productTableData,currentOperationProductId,
			shelvesDialogTitle,currentShelvesOperation,shelvesFormData,currentOperationShelvesRow,
			currentWarehouseId,productDialogVisible,productDialogTitle,productDialogBtnTitle,
			isFristShowProductForm
		}
	},
	methods:{
		productTableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
				textAlign:'center',
			}
		},
		tableRowClass(row){
			return {
				paddingBottom:'5px',
				paddingTop:'5px',
			}
		},
		//点击删除物品按钮
		deleteInventoryItemBtnEvent(row){
			if(Number(row.sum) > 0){
				this.$confirm('物品数量不为0，移除后无法恢复，是否确定移除？',{
					confirmButtonText: '确定',
				    cancelButtonText: '取消',
				    type: 'warning'
				}).then(_ => {
					// done();
				  })
				  .catch(_ => {});
			}
		},
		//点击添加产品按钮事件
		addProductBtnEvent(){
			if(this.currentOperationShelvesRow.shelvesId == null){
				this.$message({
					type: 'error',
					message: '请先选择货架!'
				});
				return;
			}
			this.productDialogBtnTitle = '确定新增';
			this.productDialogTitle = '新增产品信息';
			this.currentOperationProductId = -1;
			this.productDialogVisible = true;
			if(this.isFristShowProductForm) this.isFristShowProductForm = false;
			else this.$refs.productFormData.resetInfo();
		},
		//点击盘点产品按钮事件
		modifyProductBtnEvent(row){
			console.log('row.iiId::' + row.iiId);
			this.productDialogBtnTitle = '确定更新';
			this.productDialogTitle = '修正物品信息';
			this.currentOperationProductId = row.iiId;
			this.productDialogVisible = true;
			if(this.isFristShowProductForm) this.isFristShowProductForm = false;
			else this.$refs.productFormData.queryFun(row.iiId);
		},
		//确定添加/更新产品事件
		entryAddOrUpdateProductEvent(){
			let data = this.$refs.productFormData.formData;
			if(data.iiId == null){
				data.shelvesId = this.currentOperationShelvesRow.shelvesId;
				data.warehouseId = this.currentWarehouseId;
			}
			data.total = data.price * data.sum;//计算总价
			delete data.shelves;
			delete data.warehouse;
			// console.log(data);
			this.submitInventoryInfo(data);
		},
		//货架行点击事件
		shelvesRowClickEvent(row){
			this.currentOperationShelvesRow = row;
			this.getProductInfo(row.shelvesId);
		},
		//仓库下拉列表变更时时
		changeWarehouse(){
			// console.log("currentWarehouseId:::" + this.currentWarehouseId);
			this.getShelvesInfo(this.currentWarehouseId);
			this.productTableData = [];//清空产品列表
		},
		//点击添加仓库按钮事件
		addWarehouseBtnEvent(){
			let warehouseFormDataTemp = {
				warehouseId:null,warehouseName:'',updateDate:null,agentId:null,introduce:''
			};
			this.warehouseFormData = warehouseFormDataTemp;
			this.warehouseDialogTitle = '填写新增仓库信息';
			this.warehouseDialogBtnTitle = '确定新增';
			this.warehouseDialogVisible = true;
		},
		//点击修改当前仓库按钮事件
		modifyWarehouseBtnEvent(){
			this.getOneWarehouse(this.currentWarehouseId);
			this.warehouseDialogTitle = '修改当前仓库信息';
			this.warehouseDialogBtnTitle = '确定修改';
			this.warehouseDialogVisible = true;
		},
		//确定添加或者更新仓库事件
		entryAddOrUpdateWarehouseEvent(){
			this.warehouseFormData.agentId = this.$store.getters.employeeId;
			delete this.warehouseFormData.updateDate;
			console.log(this.warehouseFormData);
			this.submitWarehouseInfo(this.warehouseFormData);
		},
		//确定提交仓库结构数据
		entrySumbitShelvesEvent(){
			// console.log(this.shelvesFormData);
			this.submitShelvesInfo(this.shelvesFormData);
		},
		//点击添加根货架事件
		addRootShelvesBtnEvent(){
			let shelvesFormDataTemp = {
				shelvesId:null,
				shelvesName:'',
				parentId:null,
				note:'',
				warehouseId:this.currentWarehouseId,
				agentId:this.$store.getters.employeeId
			}
			this.shelvesFormData = shelvesFormDataTemp;
			this.shelvesDialogTitle = '填写新增根货架信息';
			this.shelvesDialogVisible = true;
			this.currentOperationShelvesRow = this.tableData;
		},
		//点击添加子货架事件
		addShelvesBtnEvent(row){
			this.currentOperationShelvesRow = row;
			this.currentShelvesOperation = 1;
			let shelvesFormDataTemp = {
				shelvesId:null,
				shelvesName:'',
				parentId:row.shelvesId,
				note:'',
				warehouseId:row.warehouseId,
				agentId:this.$store.getters.employeeId
			}
			this.shelvesFormData = shelvesFormDataTemp;
			this.shelvesDialogTitle = '填写新增子货架信息';
			this.shelvesDialogVisible = true;
		},
		//点击修改货架事件
		modifyShelvesBtnEvent(row){
			this.currentOperationShelvesRow = row;
			this.currentShelvesOperation = 0;
			let shelvesFormDataTemp = {
				shelvesId:row.shelvesId,
				shelvesName:row.shelvesName,
				parentId:row.parentId,
				note:row.note,
				warehouseId:row.warehouseId,
				agentId:this.$store.getters.employeeId
			}
			this.shelvesFormData = shelvesFormDataTemp;
			this.shelvesDialogTitle = '修改货架信息';
			this.shelvesDialogVisible = true;
		},
		//点击删除货架事件
		deleteShelvesBtnEvent(){
			
		},
		entryApply(){
			this.$confirm('开单后无法修改，请谨慎操作，是否开单？','提示',{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(_ => {
				// done();
				this.inquiryDialogVisible = false;
			  })
			  .catch(_ => {});
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
			  .then(_ => {
				done();
			  })
			  .catch(_ => {});
		},
		//提交单个产品的数据
		submitInventoryInfo(data){
			submitDataForPost("/inventory-item/insert-update",data).then(res=>{
				let newItem = res.data.data;
				//设置所属仓库名
				for(let k=0;k<this.warehouseData.length;k++){
					if(this.warehouseData[k].warehouseId == this.currentWarehouseId){
						newItem.warehouse = {
							warehouseName:this.warehouseData[k].warehouseName
						};
						break;
					}
				}
				//设置所属货架名
				newItem.shelves={
					shelvesName:this.currentOperationShelvesRow.shelvesName
				};
				//判断是新增还是修改
				if(data.iiId == null){
					this.productTableData.push(newItem);
				}else{
					for(let i=0;i<this.productTableData.length;i++){
						if(this.productTableData[i].iiId == data.iiId){
							this.productTableData[i] = newItem;
							break;
						}
					}
				}
				this.productDialogVisible = false;
				this.$message({
					type: 'success',
					message: '操作成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '操作失败!'
				});
			});
		},
		//拉取货架下的物品清单
		async getProductInfo(shelvesId){
			let params = {};
			params.shelvesId = shelvesId;
			GetForParams("/inventory-item/all",params).then(res=>{
				this.productTableData = res.data.data;
				// console.log(this.productTableData);
			}).catch(err=>{
				console.log(err);
			});
		},
		//提交货架的数据
		submitShelvesInfo(data){
			submitDataForPost("/warehouse-shelves/insert-update",data).then(res=>{
				let newShelves = res.data.data;
				if(data.shelvesId > 0){
					this.currentOperationShelvesRow.shelvesName = newShelves.shelvesName;
					this.currentOperationShelvesRow.note = newShelves.note;
					this.currentOperationShelvesRow.updateDate = newShelves.updateDate;
					this.currentOperationShelvesRow.agentId = newShelves.agentId;
				}else{
					newShelves.shelvesList = [];
					if(this.currentOperationShelvesRow == this.tableData){
						this.tableData.unshift(newShelves);
					}else{
						this.currentOperationShelvesRow.shelvesList.unshift(newShelves);
					}
				}
				this.shelvesDialogVisible = false;
				this.$message({
					type: 'success',
					message: '操作成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '操作失败!'
				});
			});
		},
		//提交仓库数据
		submitWarehouseInfo(data){
			submitDataForPost("/warehouse/insert-update",data).then(res=>{
				let newWarehouse = res.data.data;
				if(Number(data.warehouseId) > 0){
					for(let i=0;i<this.warehouseData.length;i++){
						if(this.warehouseData[i].warehouseId == data.warehouseId){
							this.warehouseData[i] = newWarehouse;
							break;
						}
					}
				}else{
					this.warehouseData.push(newWarehouse);
				}
				this.warehouseDialogVisible = false;
				this.$message({
					type: 'success',
					message: '操作成功!'
				});
			}).catch(err=>{
				console.log(err.data);
				this.$message({
					type: 'error',
					message: '操作失败!'
				});
			});
		},
		//拉取仓库的树形结构信息
		async getShelvesInfo(warehouseId){
			let params = {};
			params.warehouseId = warehouseId;
			GetForParams("/warehouse-shelves/all",params).then(res=>{
				this.tableData = res.data.data;
				// console.log(this.tableData);
			}).catch(err=>{
				console.log(err);
			});
		},
		//拉取单个仓库信息
		async getOneWarehouse(warehouseId){
			let params = {};
			params.warehouseId = warehouseId;
			GetForParams("/warehouse/one",params).then(res=>{
				this.warehouseFormData = res.data.data;
			}).catch(err=>{
				console.log(err);
			});
		},
		//拉取所有仓库信息
		async initWarehouse(){
			getDataForNullParams("/warehouse/all").then(res=>{
				this.warehouseData = res.data.data;
				if(this.warehouseData.length > 0){
					this.getShelvesInfo(this.warehouseData[0].warehouseId);
					this.currentWarehouseId = this.warehouseData[0].warehouseId;
				}
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

<style lang="scss">
@import url("../../assets/css/Public/public.scss");

.warehouse-select-item{
	margin-right: 10px;
}
#warehouse-shelves-info .el-dialog__body{
	padding-top: 10px;
	padding-bottom: 10px;
}
#warehouse-info-header{
	width: 100%;
	height: 40px;
	color: #fff;
	text-align: center;
	line-height: 40px;
	font-size: 16px;
	background-color: #909399;
}
#warehouse-info{
	width: 12%;
	height: 300px;
	float: left;
	border: 1px #ccc solid;
	border-radius: 8px;
	overflow: hidden;
}
#warehouse-info li{
	list-style: none;
	list-style-type: none;
	cursor: pointer;
	line-height: 30px;
}
#shelves-table{

}
.activeWarehouseItem{
	background-color: #67C23A;
	color: #ffffff;
}
#product-table{
	margin-top: 20px;
}
</style>
