
<div class="col-md-6 col-md-offset-5 ">

	<h3 class="header">Profile Details</h3>

</div>

<div class="row">
	<div class="col-md-6 col-md-offset-3">


		<form class="col s12">

			<div class="row">
				<div class="input-field col s6">
					<i class="material-icons prefix">account_circle</i> <input
						id="icon_prefix" type="text" value="${firstName}" class="validate">
					<label for="icon_prefix">First Name</label>
				</div>
				<div class="input-field col s6">
					<i class="material-icons prefix"></i> <input id="icon_lastname"
						type="text" value="${lastName}" class="validate"> <label
						for="icon_lastname">Last Name</label>
				</div>
			</div>

			<div class="row">
				<div class="input-field col s6">
					<i class="material-icons prefix">phone</i> <input
						id="icon_telephone" type="tel" value="${telephone}"
						class="validate"> <label for="icon_telephone">Telephone</label>
				</div>

				<div class="input-field col s6">
					<i class="material-icons prefix">email</i> <input id="email"
						type="email" value="${email}" class="validate"> <label
						for="email">Email</label>
				</div>
			</div>



			<div class="row">
				<div class="input-field col s6">
					<i class="material-icons prefix">password</i> <input id="password"
						type="password" value="${password}" class="validate"> <label
						for="password">Password</label>
				</div>	

				<div class="input-field col s6">
					<i class="material-icons prefix">password</i> <input id="password"
						type="password" value="${confirmpassword}" class="validate">
					<label for="Re-type password">Confirm Password</label>
				</div>
			</div>

			<div class="row">
				<div class="input-field col s12">
					<i class="material-icons prefix">Address</i> <input id="address"
						type="text" value="${address}" class="validate"> <label
						for="address">Address</label>
				</div>
			</div>



			<div class="row">
				<div class="input-field col s6">
					<i class="material-icons prefix"></i> <input id="City" type="text"
						value="${city}" class="validate"> <label for="City">City</label>
				</div>

				<div class="input-field col s6">
					<i class="material-icons prefix"></i> <input id="ZipCode"
						type="text" value="${zipcode}" class="validate"> <label
						for="ZipCode">ZipCode</label>
				</div>
			</div>


			<div class="row">
				<div class="input-field col s6">
					<i class="material-icons prefix"></i> <input id="State" type="text"
						value="${state}" class="validate"> <label for="State">State</label>
				</div>

				<div class="input-field col s6">
					<i class="material-icons prefix"></i> <input id="Country"
						type="text" value="${country}" class="validate"> <label
						for="Country">Country</label>
				</div>
			</div>
		</form>

	</div>

	<div class="row">
		<div class="col s4 offset-s5 ">
			<button class="btn waves-effect waves-light" type="submit"
				name="action">
				Update <i class="material-icons right">send</i>
			</button>
		</div>
	</div>
</div>



