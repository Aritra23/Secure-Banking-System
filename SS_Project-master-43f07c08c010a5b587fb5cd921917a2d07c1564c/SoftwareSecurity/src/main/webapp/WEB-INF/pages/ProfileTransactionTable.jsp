
<div class="row">
	<div class="col-md-6 col-md-offset-3">

		<table class="striped">
			<thead>
				<tr>
					<th data-field="">Transaction Id</th>
					<th data-field=""></th>
					<th data-field="">Authorized</th>
				</tr>
			</thead>

		</table>

		<c:if test="${error=='false'}">
			<div class="row"></div>
			<h5 class="text-center">${errorMessage}</h5>
			<div class="row"></div>
		</c:if>
		<ul class="collapsible" data-collapsible="accordion">
			<c:forEach var="Transaction" items="${transactionRecur}">
				<li>
					<div class="collapsible-header">${Transaction.getProfileTransactionId()}<span
							style="margin-left: 28pc"></span> <a href="#">Approve</a>
					</div>
					<div class="collapsible-body">



						<%--Dynamic page loading should be created so as to  --%>
						>
						<table class="striped">


							<tr>
								<td><span style="margin-left: 1pc"></span> UserName:
									${Transaction.getUserName()}</td>
								<td>Address: ${Transaction.getAddress()}</td>
								<td>City: ${Transaction.getCity()}</td>
							</tr>
							<tr>
								<td><span style="margin-left: 1pc"></span>
									Email:${Transaction.getEmail()}</td>
								<td>Phone number: ${Transaction.getPhoneNumber()}</td>
								<td>State: ${Transaction.getState()}</td>
							</tr>
							<tr>
								<td><span style="margin-left: 1pc"></span>
									Status:${Transaction.getStatus()}</td>
								<td>Comment: ${Transaction.getComment()}</td>
								<td>Time Stamp ${Transaction.getTimestamp()}</td>
							</tr>
						</table>
						<form:form commandName="transactionId" action="/cse/manager/"
							method="post" modelAttribute="transactionId">
							<input type="hidden" name="tranId1"
								value="${Transaction.getProfileTransactionId()}" />
							<table>
								<tr>
									<td><label for="sel2">Select Validator (hold shift
											to select more than one):</label> <select multiple
										class="form-control" id="Validator" name="Validator">
											<c:forEach var="Validators" items="${validatorNames}">
												<option value="${Validators}">${Validators}</option>
											</c:forEach>
											<option selected="selected" value="e$$oR"></option>
									</select></td>
									<td>
										<div class="dropdown">
											<select class="form-control" id="BoolVal" name="BoolVal">
												<option value=true>Approve</option>
												<option value=false>Decline</option>
											</select>
										</div>

									</td>
									<td>
										<button id="submit" class="btn waves-effect waves-light"
											type="submit" name="action">
											Submit <i class="material-icons right">send</i>
										</button>
									</td>


								</tr>
								<%--Script should be inserted here to check weather the text box is filled before approving or not --%>


							</table>
						</form:form>

					</div>
				</li>
			</c:forEach>
		</ul>

	</div>
</div>

