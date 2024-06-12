document.addEventListener("DOMContentLoaded", function() {
	const token = '03a196a7-9195-11ee-8bfa-8a2dda8ec551'; // Thay thế bằng token thực tế
	const provinceSelect = document.getElementById("province");
	const districtSelect = document.getElementById("district");
	const wardSelect = document.getElementById("ward");

	const addressInput = document.getElementById("address");
	const showSelectionButton = document.getElementById("showSelection");
	const selectedDistrictText = document.getElementById("selectedDistrict");
	document.addEventListener("DOMContentLoaded", function() {
		var provinceSelect = document.getElementById("province");
		var districtSelect = document.getElementById("district");
		var wardSelect = document.getElementById("ward");
		var khCity = document.getElementById("kh_city");
		var khQuan = document.getElementById("kh_quan");
		var khXa = document.getElementById("kh_xa");

		function updateDistrictOptions(districts) {
			// Xóa các option hiện có trong districtSelect và wardSelect
			while (districtSelect.options.length > 1) {
				districtSelect.remove(1);
			}
			while (wardSelect.options.length > 1) {
				wardSelect.remove(1);
			}

			// Thêm các option mới vào districtSelect
			districts.forEach(function(district) {
				var option = document.createElement('option');
				option.value = district.DistrictID;
				option.text = district.DistrictName;
				districtSelect.add(option);
			});

			// Cập nhật trường ẩn cho quận/huyện và tỉnh/thành
			khQuan.value = districtSelect.options[districtSelect.selectedIndex].text;
			khCity.value = provinceSelect.options[provinceSelect.selectedIndex].text;
		}

		function updateWardOptions(wards) {
			// Xóa các option hiện có trong wardSelect
			while (wardSelect.options.length > 1) {
				wardSelect.remove(1);
			}

			// Thêm các option mới vào wardSelect
			wards.forEach(function(ward) {
				var option = document.createElement('option');
				option.value = ward.WardCode;
				option.text = ward.WardName;
				wardSelect.add(option);
			});

			// Cập nhật trường ẩn cho xã/phường
			khXa.value = wardSelect.options[wardSelect.selectedIndex].text;
		}

		provinceSelect.addEventListener("change", function() {
			var selectedProvinceCode = provinceSelect.value;

			const url_district = `https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=${selectedProvinceCode}`;
			fetch(url_district, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					'token': '03a196a7-9195-11ee-8bfa-8a2dda8ec551'
				}
			})
				.then(response => response.json())
				.then(data => {
					if (data.data && Array.isArray(data.data)) {
						updateDistrictOptions(data.data);
					} else {
						console.error("Lỗi khi lấy danh sách quận/huyện: Dữ liệu không phải là mảng.", data);
					}
				}).catch(error => console.error('Error:', error));
		});

		districtSelect.addEventListener("change", function() {
			var selectedDistrictCode = parseInt(districtSelect.value, 10);
			var selectedProvinceCode = provinceSelect.value;

			fetch('https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					'token': '03a196a7-9195-11ee-8bfa-8a2dda8ec551'
				},
				body: JSON.stringify({
					"district_id": selectedDistrictCode
				})
			})
				.then(response => response.json())
				.then(data => {
					if (data.data && Array.isArray(data.data)) {
						updateWardOptions(data.data);
					} else {
						console.error("Lỗi khi lấy danh sách xã/phường: Dữ liệu không phải là mảng.", data);
					}

					// Cập nhật trường ẩn cho quận/huyện khi bạn chọn một quận/huyện khác
					khQuan.value = districtSelect.options[districtSelect.selectedIndex].text;
				});

			// Cập nhật trường ẩn cho tỉnh/thành khi bạn chọn một quận/huyện khác
			khCity.value = provinceSelect.options[provinceSelect.selectedIndex].text;
		});

		wardSelect.addEventListener("change", function() {
			khXa.value = wardSelect.options[wardSelect.selectedIndex].text;
		});

		// Lấy danh sách tỉnh/thành phố ban đầu
		fetch("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province", {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				'Token': '03a196a7-9195-11ee-8bfa-8a2dda8ec551'
			},
		})
			.then(response => response.json())
			.then(data => {
				if (Array.isArray(data.data)) {
					data.data.forEach(function(province) {
						var option = document.createElement('option');
						option.value = province.ProvinceID;
						option.text = province.ProvinceName;
						provinceSelect.add(option);
					});
					// Kích hoạt sự kiện change ban đầu để thiết lập giá trị mặc định cho select của quận/huyện và xã/phường
					provinceSelect.dispatchEvent(new Event('change'));
				} else {
					console.error("Lỗi khi lấy danh sách tỉnh/thành phố: Dữ liệu không phải là mảng.", data);
				}
			}).catch(error => console.error("Lỗi khi lấy danh sách tỉnh/thành phố: ", error));
	});

	document.getElementById('district').addEventListener('change', calculateShippingFee);
	document.getElementById('ward').addEventListener('change', calculateShippingFee);

	function calculateShippingFee() {
		var district_id = document.getElementById("district").value;
		var ward_code_1 = document.getElementById("ward").value;
		var ward_code_2 = document.getElementById("kh_xa").value;

		var ward_code = ward_code_1 === "Chọn xã/phường" ? ward_code_2 : ward_code_1;

		const data = {
			"from_district_id": 1454,
			"from_ward_code": "21207",
			"service_id": 53320,
			"service_type_id": null,
			"to_district_id": parseInt(district_id, 10),
			"to_ward_code": ward_code,
			"height": 50,
			"length": 20,
			"weight": 200,
			"width": 20,
			"insurance_value": 10000,
			"cod_failed_amount": 0,
			"coupon": null
		};

		fetch('https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'Token': '03a196a7-9195-11ee-8bfa-8a2dda8ec551',
				'ShopId': '190366',
			},
			body: JSON.stringify(data)
		})
			.then(response => response.json())
			.then(result => {
				if (result.data) {
					const total = parseFloat(result.data.total); 
					// Cập nhật phí ship
					document.getElementById("fee_ship").innerText = total.toLocaleString() + ' VNĐ';
					document.getElementById("fee_ship_value").value = total;
					
					let totalProduct = parseFloat(document.getElementById("priceMoney").value);
					let totalMoney = total + totalProduct;
					document.getElementById("finallyMoney").value = totalMoney;
					document.getElementById("textMoney").innerText = totalMoney.toLocaleString() + ' VNĐ';
					console.log(document.getElementById("textMoney").value)
				} else {
					console.error('Error in API response:', result);
				}
			}).catch(error => console.error('Error:', error));
	};

	function updateDistrictOptions(districts) {
		districtSelect.innerHTML = '<option value="" disabled selected>Chọn quận/huyện</option>';
		wardSelect.innerHTML = '<option value="" disabled selected>Chọn xã/phường</option>';

		districts.forEach(district => {
			const option = document.createElement('option');
			option.value = district.DistrictID;
			option.textContent = district.DistrictName;
			districtSelect.appendChild(option);
		});
	}

	function updateWardOptions(wards) {
		wardSelect.innerHTML = '<option value="" disabled selected>Chọn xã/phường</option>';

		wards.forEach(ward => {
			const option = document.createElement('option');
			option.value = ward.WardCode;
			option.textContent = ward.WardName;
			wardSelect.appendChild(option);
		});
	}

	provinceSelect.addEventListener("change", function() {
		const selectedProvinceId = this.value;
		fetch(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=${selectedProvinceId}`, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				'token': token
			}
		})
			.then(response => response.json())
			.then(data => {
				if (data.data && Array.isArray(data.data)) {
					updateDistrictOptions(data.data);
				} else {
					console.error("Lỗi khi lấy danh sách quận/huyện:", data);
				}
			})
			.catch(error => console.error('Error:', error));
	});

	districtSelect.addEventListener("change", function() {
		const selectedDistrictId = parseInt(this.value, 10);
		fetch('https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'token': token
			},
			body: JSON.stringify({ "district_id": selectedDistrictId })
		})
			.then(response => response.json())
			.then(data => {
				if (data.data && Array.isArray(data.data)) {
					updateWardOptions(data.data);
				} else {
					console.error("Lỗi khi lấy danh sách xã/phường:", data);
				}
			})
			.catch(error => console.error('Error:', error));
	});

	// Lấy danh sách tỉnh/thành phố ban đầu
	fetch("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province", {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json',
			'Token': token
		},
	})
		.then(response => response.json())
		.then(data => {
			if (Array.isArray(data.data)) {
				data.data.forEach(province => {
					const option = document.createElement('option');
					option.value = province.ProvinceID;
					option.textContent = province.ProvinceName;
					provinceSelect.appendChild(option);
				});
			} else {
				console.error("Lỗi khi lấy danh sách tỉnh/thành phố:", data);
			}
		})
		.catch(error => console.error("Lỗi khi lấy danh sách tỉnh/thành phố: ", error));

	// Xử lý sự kiện khi nút được nhấn
	showSelectionButton.addEventListener("click", function() {
		const selectedProvinceOption = provinceSelect.options[provinceSelect.selectedIndex];
		const selectedDistrictOption = districtSelect.options[districtSelect.selectedIndex];
		const selectedWardOption = wardSelect.options[wardSelect.selectedIndex];
		const address = addressInput.value.trim();

		if (selectedProvinceOption && selectedDistrictOption && selectedWardOption &&
			selectedProvinceOption.value && selectedDistrictOption.value && selectedWardOption.value && address) {
			const selectedDistrict = `${address}, ${selectedWardOption.textContent}, ${selectedDistrictOption.textContent}, ${selectedProvinceOption.textContent}`;
			selectedDistrictText.textContent = selectedDistrict;
			document.getElementById("selectedDistrict").value = selectedDistrict; // Set value to input
		} else {
			selectedDistrictText.textContent = "Vui lòng chọn đầy đủ tỉnh/thành phố, quận/huyện, xã/phường và nhập địa chỉ cụ thể.";
		}
	});
});
