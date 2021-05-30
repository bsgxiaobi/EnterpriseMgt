const menus = [
		{
			label: '面板主页',
			icon: 'location',
			isShow:true,
			did:0,
			children: [
				{
					path: '/dashboard/workplace',
					name: 'workpack',
					label: '工作台',
					icon: 'setting',
					isShow:true,
					pid:0,
				},
			]
		},
		{
			label: '个人中心',
			icon: 'location',
			isShow:true,
			did:0,
			children: [
				{
					path: '/center/self',
					name: 'self',
					label: '个人资料',
					icon: 'setting',
					isShow:true,
					pid:0,
				},
			]
		},
		{
			label: '客户关系管理',
			icon: 'location',
			isShow:false,
			did:2,
			children: [
				{
					path: '/customer/company',
					name: 'company',
					label: '客户单位',
					icon: 'setting',
					isShow:false,
					pid:15,
				},
				/* {
					path: '/customer/opportunity',
					name: 'project',
					label: '项目机会',
					icon: 'setting',
					url: 'Other/PageTwo'
				}, */
				/* {
					path: '/customer/contract',
					name: 'contract',
					label: '项目合同',
					icon: 'setting',
					url: 'Other/PageTwo'
				}, */
				{
					path: '/customer/visit',
					name: 'visit',
					label: '客户跟踪回访',
					icon: 'setting',
					isShow:false,
					pid:15,
				}
			]
		},
		{
			label: '人事管理',
			icon: 'location',
			isShow:true,
			did:0,
			children: [
				{
					path: '/personal/recruit',
					name: 'recruit',
					label: '招聘管理',
					icon: 'setting',
					isShow:false,
					pid:20,
				},
				{
					path: '/personal/employee',
					name: 'employee',
					label: '员工管理',
					icon: 'setting',
					isShow:false,
					pid:22,
				},
				/* {
					path: '/personal/salary',
					name: 'salary',
					label: '薪资管理',
					icon: 'setting',
					url: 'Other/PageTwo'
				}, */
				{
					path: '/personal/department',
					name: 'department',
					label: '部门管理',
					icon: 'setting',
					isShow:false,
					pid:22,
				},
				{
					path: '/personal/rewards-punishments',
					name: 'rewards_punishments',
					label: '奖惩管理',
					icon: 'setting',
					isShow:true,
					pid:0,
				},
				{
					path: '/personal/attendance',
					name: 'attendance',
					label: '考勤管理',
					icon: 'setting',
					isShow:true,
					pid:0,
				}
			]
		},
		{
			label: '采购管理',
			icon: 'location',
			isShow:false,
			did:4,
			children: [
				{
					path: '/purchase/apply',
					name: 'apply',
					label: '采购申请单',
					icon: 'setting',
					isShow:false,
					pid:16,
				},
				{
					path: '/purchase/inquiry',
					name: 'inquiry',
					label: '采购询价单',
					icon: 'setting',
					isShow:false,
					pid:18,
				},
			]
		},
		{
			label: '销售管理',
			icon: 'location',
			isShow:false,
			did:6,
			children: [
				{
					path: '/sales/quotation',
					name: 'quotation',
					label: '销售报价单',
					icon: 'setting',
					isShow:false,
					pid:23,
				},
				{
					path: '/sales/after',
					name: 'after',
					label: '销售售后单',
					icon: 'setting',
					isShow:false,
					pid:24,
				},
			]
		},
		{
			label: '仓库管理',
			icon: 'location',
			isShow:false,
			did:7,
			children: [
				{
					path: '/warehouse/struct',
					name: 'struct',
					label: '仓库结构',
					icon: 'setting',
					isShow:false,
					pid:0,
				},
				{
					path: '/warehouse/delivery',
					name: 'delivery',
					label: '销售出库单',
					icon: 'setting',
					isShow:false,
					pid:28,
				},
				{
					path: '/warehouse/warehousing',
					name: 'warehousing',
					label: '采购入库单',
					icon: 'setting',
					isShow:false,
					pid:27,
				},
				{
					path: '/warehouse/claim',
					name: 'claim',
					label: '支领发放单',
					icon: 'setting',
					isShow:false,
					pid:28,
				},
				{
					path: '/warehouse/detail',
					name: 'detail',
					label: '库存明细账',
					icon: 'setting',
					isShow:false,
					pid:0,
				}
			]
		},
		{
			label: '财务管理',
			icon: 'location',
			isShow:false,
			did:1,
			children: [
				{
					path: '/finance/receivable',
					name: 'Receivable',
					label: '应收应付',
					icon: 'setting',
					isShow:false,
					pid:11,
				},
				/* {
					path: '/page2',
					name: 'page8',
					label: '固定资产',
					icon: 'setting',
					url: 'Other/PageTwo'
				}, */
				{
					path: '/finance/accounting',
					name: 'Accounting',
					label: '账务处理',
					icon: 'setting',
					isShow:false,
					pid:11,
				},
				/* {
					path: '/page2',
					name: 'page9',
					label: '出纳管理',
					icon: 'setting',
					url: 'Other/PageTwo'
				}, */
				{
					path: '/page2',
					name: 'page9',
					label: '报表与分析',
					icon: 'setting',
					isShow:false,
					pid:0,
				}
			]
		}
	];
	
export {
	menus
};