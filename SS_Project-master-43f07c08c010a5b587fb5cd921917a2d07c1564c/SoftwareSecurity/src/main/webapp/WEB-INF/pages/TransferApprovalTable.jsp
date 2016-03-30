
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
			<c:forEach var="Transaction" items="${transactions}">
				<li>
					<div class="collapsible-header">${Transaction.getFundTransactionId()}<span
							style="margin-left: 28pc"></span> <a href="#">Approve</a>
					</div>
					<div class="collapsible-body">



						<%--Dynamic page loading should be created so as to  --%>
						>
						<table class="striped">


							<tr>
								<td><span style="margin-left: 1pc"></span> Transaction Id:
									${Transaction.getFundTransactionId()}</td>
								<td>User Id: ${Transaction.getUserId()}</td>
								<td>Transaction Type:
									${Transaction.getTransactionType()}</td>
							</tr>
							<tr>
								<td><span style="margin-left: 1pc"></span> Source Account:
									${Transaction.getSourceAccount()}</td>
								<td>Destination Account:
									${Transaction.getDestinationAccount()}</td>
								<td>Amount: ${Transaction.getAmount()}</td>
							</tr>
							<tr>
								<td><span style="margin-left: 1pc"></span>
									Status:${Transaction.getStatus()}</td>
								<td>Date and Time: ${Transaction.getTimeStamp()}</td>
							</tr>
						</table>
						<form:form commandName="transactionId" action="/cse/manager/CTrequest" method="post" modelAttribute="transactionId">
							<input type="hidden" name="tranId1"
								value="${Transaction.getFundTransactionId()}" />
							<table>
								<tr>

									<td><span style="margin-left: 2pc"></span></td>
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

