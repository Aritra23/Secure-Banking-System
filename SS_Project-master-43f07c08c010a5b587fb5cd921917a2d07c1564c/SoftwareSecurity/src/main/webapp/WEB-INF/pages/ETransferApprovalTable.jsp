	<jsp:include page="Crightsidebar.jsp" />



<div class="row">

		<div class="col-md-6 col-md-offset-4 ">

			<h3 class="header">
			Transfer Approval 
			</h3>

		</div>
	</div>


<div class="row">
	<div class="col-md-6 col-md-offset-3">

		<table class="striped">
			<thead>
			
			</thead>

		</table>

		<c:if test="${error=='false'}">
			<div class="row"></div>
			<h5 class="text-center">${errorMessage}</h5>
			<div class="row"></div>
		</c:if>
		
		<ul class="collapsible" data-collapsible="accordion">
			<c:forEach var="PendingTransaction" items="${pendingTransactions}">
				<li>
					<div class="collapsible-header"><b>Transaction Id: ${PendingTransaction.getFundTransactionId()}</b><span
							style="margin-left: 28pc"></span> <a href="#">&#8600;</a>
					</div>
					<div class="collapsible-body">



						<%--Dynamic page loading should be created so as to  --%>
						
						<table class="striped">
						
							<tr>
								<td><span style="margin-left: 1pc"></span><b>Transaction Id:</b></td>
								<td>	${PendingTransaction.getFundTransactionId()}</td>
							</tr>
								
							<tr>	
								<td><span style="margin-left: 1pc"></span><b>User Id:</b></td>
								<td> ${PendingTransaction.getUserId()}</td>
							</tr>
								
							<tr>
								<td><span style="margin-left: 1pc"></span><b>Transaction Type:</b></td>
									<td>${PendingTransaction.getTransactionType()}</td>
							</tr>
							
							<tr>
								<td><span style="margin-left: 1pc"></span> <b>Source Account:</b></td>
								<td>$ ${PendingTransaction.getSourceAccount()}</td>
							</tr>	
							
							<tr>			
								<td><span style="margin-left: 1pc"></span><b>Destination Account:</b></td>
								<td>${PendingTransaction.getDestinationAccount()}</td>
							</tr>
							
							<tr>		
								<td><span style="margin-left: 1pc"></span><b>Amount:</b></td>
								<td> $ ${PendingTransaction.getAmount()}</td>
							</tr>
							
							<tr>
								<td><span style="margin-left: 1pc"></span><b>Status:</b></td>
								<td>${PendingTransaction.getStatus()}</td>
							</tr>
							
							<tr>	
								<td><span style="margin-left: 1pc"></span><b>Date and Time:</b></td>
								<td> ${PendingTransaction.getTimeStamp()}</td>
							</tr>
							
							
							
						</table>
						<form:form action="/cse/employee" method="POST" id = "pendingTransaction${PendingTransaction.getFundTransactionId()}" commandName="updatedTransactionId" modelAttribute ="updatedTransactionId">		
						<input type="hidden" name="updatedTransactionId" value="${PendingTransaction.getFundTransactionId()}"/>							<table>
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
								<%--Script should be inserted here to check whether the text box is filled before approving or not --%>


							</table>
						</form:form>

					</div>
				</li>
			</c:forEach>
		</ul>

	</div>
</div>

