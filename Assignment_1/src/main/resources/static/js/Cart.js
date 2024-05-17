const unitPrice = 280000; // Giá mỗi đơn vị

	function updateTotalPrice() {
		var quantityInput = document.getElementById('quantity-input');
		var quantityDisplay = document.getElementById('quantity-display');
		var totalPriceElement = document.getElementById('total-price');

		var quantity = parseInt(quantityInput.value);
		if (!isNaN(quantity)) {
			quantityDisplay.textContent = quantity;
			var totalPrice = unitPrice * quantity;
			totalPriceElement.textContent = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalPrice);
		}
	}

	document.getElementById('button-decrement').addEventListener('click', function() {
		var quantityInput = document.getElementById('quantity-input');
		var currentValue = parseInt(quantityInput.value);
		if (!isNaN(currentValue) && currentValue > 1) {
			quantityInput.value = currentValue - 1;
			updateTotalPrice();
		}
	});

	document.getElementById('button-increment').addEventListener('click', function() {
		var quantityInput = document.getElementById('quantity-input');
		var currentValue = parseInt(quantityInput.value);
		if (!isNaN(currentValue)) {
			quantityInput.value = currentValue + 1;
			updateTotalPrice();
		}
	});

	// Cập nhật thành tiền khi tải trang
	document.addEventListener('DOMContentLoaded', updateTotalPrice);