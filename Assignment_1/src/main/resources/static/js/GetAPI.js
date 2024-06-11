document.addEventListener("DOMContentLoaded", function() {
	const token = '03a196a7-9195-11ee-8bfa-8a2dda8ec551'; // Thay thế bằng token thực tế
	const provinceSelect = document.getElementById("province");
	const districtSelect = document.getElementById("district");
	const wardSelect = document.getElementById("ward");
	const addressInput = document.getElementById("address");
	const showSelectionButton = document.getElementById("showSelection");
	const selectedDistrictText = document.getElementById("selectedDistrict");

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
