# Banking System — Technical & Architectural Documentation

**Status:** Phase 7 Complete — Ready for Implementation

---

## 📌 Development Lifecycle & Process Phases

- [x] Phase 1: Understand Problem
- [x] Phase 2: Discover Domain
- [x] Phase 3: Find Responsibilities
- [x] Phase 4: Design Objects
- [x] Phase 5: Design Collaborations
- [x] Phase 6: Design Storage
- [x] Phase 7: Design Use Cases
- [ ] Phase 8: Implement
- [ ] Phase 9: Refactor

---

## 🏗️ Responsibility Model

### Customer

#### Responsibilities

- Maintain account ownership relationship
- Maintain customer lifecycle state
- Provide information about owned accounts

#### Lifecycle

- Active
- Blocked

---

### Account

#### Responsibilities

- Deposit money
- Withdraw money
- Transfer money
- Provide balance information
- Become suspended
- Become active
- Protect balance rules
- Protect account status rules

#### Lifecycle

- Active
- Suspended
- Blocked

---

### Transaction

#### Responsibilities

- Maintain transaction lifecycle
- Provide transaction information
- Protect transaction creation rules
- Protect transaction lifecycle transitions
- Represent a money movement event

#### Lifecycle

- Created
- Pending
- Succeeded

---

### Bank

#### Responsibilities

- Open accounts
- Locate customers
- Locate accounts
- Locate transactions
- Coordinate banking operations
- Enforce bank-level policies

---

## 🏷️ Object Properties

### Customer

- id
- name
- status
- accounts

### Account

- balance
- status
- accountType

### Transaction

- amount
- createdAt
- status

### Bank

- customers
- accounts
- transactions

---

## 🔗 Relationship Model

### Customer

Owns:

- Nothing

References:

- Accounts

Referenced By:

- Bank

---

### Account

Owns:

- Nothing

References:

- Transactions

Referenced By:

- Customer
- Bank
- Transaction

---

### Transaction

Owns:

- Nothing

References:

- Account

Referenced By:

- Bank
- Account

---

### Bank

Owns:

- Customers
- Accounts
- Transactions

References:

- Customers
- Accounts
- Transactions

Referenced By:

- Application

---

## 📜 Business Rules Discovered So Far

### Customer

- Customer maintains ownership of Accounts.
- Customer has a lifecycle.

### Account

- Account owns balance-related decisions.
- Account owns withdrawal decisions.
- Account status affects available operations.

### Transaction

- Transaction has a lifecycle.
- Transaction represents a money movement event.
- Transaction protects its lifecycle transitions.

### Bank

- Bank coordinates banking operations.
- Bank enforces bank-level policies.

---

## 🤝 Collaboration Model (Draft)

### Use Case: Open Account

Actor:

- User

Goal:

- Open a new account.

Objects Involved:

- Application
- Bank
- Customer
- Account

Flow:

1. Application shows menu.
2. User selects Open Account.
3. Application collects:
   - Customer identifier
   - Customer name
   - Account type
4. Application requests account opening from Bank.
5. Bank locates Customer.
6. If Customer does not exist:
   - Create Customer.
7. If Customer exists:
   - Verify account-opening eligibility.
8. Bank creates Account.
9. Bank associates Account with Customer.
10. Application presents the result.

---

### Use Case: Deposit Money

Actor:

- User

Goal:

- Deposit money into an existing account.

Objects Involved:

- Application
- Bank
- Account
- Transaction

Flow:

1. Application shows menu.
2. User selects Deposit Money.
3. Application collects:
   - Account identifier
   - Deposit amount
4. Application requests deposit from Bank.
5. Bank locates Account.
6. If Account does not exist:
   - Application presents failure result.
7. Bank requests Account to perform deposit.
8. Account verifies deposit eligibility according to its rules and status.
9. Account updates its balance.
10. Bank creates Transaction.
11. Transaction validates its creation rules.
12. Transaction records the money movement event.
13. Application presents success result.

---

### Use Case: Withdraw Money

Actor:

- User

Goal:

- Withdraw money from an existing account.

Objects Involved:

- Application
- Bank
- Account
- Transaction

Flow:

1. Application shows menu.
2. User selects Withdraw Money.
3. Application collects:
   - Account identifier
   - Withdrawal amount
4. Application requests withdrawal from Bank.
5. Bank locates Account.
6. If Account does not exist:
   - Application presents failure result.
7. Bank requests Account to perform withdrawal.
8. Account verifies withdrawal eligibility according to its rules and status.
9. Account verifies sufficient funds according to balance rules.
10. If withdrawal is not allowed:
    - Application presents failure result.
11. If withdrawal is allowed:
    - Account updates balance.
12. Bank creates Transaction.
13. Transaction validates its creation rules.
14. Transaction records the money movement event.
15. Application presents success result.

---

### Use Case: Transfer Money

Actor:

- User

Goal:

- Transfer money from one existing account to another existing account.

Objects Involved:

- Application
- Bank
- Account
- Transaction

Flow:

1. Application shows menu.
2. User selects Transfer Money.
3. Application collects:
   - Source Account identifier
   - Destination Account identifier
   - Transfer amount
4. Application requests transfer from Bank.
5. Bank locates both Accounts.
6. If either Account does not exist:
   - Application presents failure result.
7. Bank requests source Account to perform transfer.
8. Source Account verifies transfer eligibility according to its rules and status.
9. Source Account verifies sufficient funds according to balance rules.
10. If transfer is not allowed:
    - Application presents failure result.
11. If transfer is allowed:
    - Accounts update balances according to transfer rules.
12. Bank creates Transaction.
13. Transaction validates its creation rules.
14. Transaction records the money movement event.
15. Application presents success result.

---

## 🚧 Open Questions

### Customer

- Are there additional customer-specific rules?

### Account

- What exact balance rules exist?

### Transaction

- What is its exact role during transfer operations?
- What are its exact relationships with accounts?

### Bank

- What are the exact bank-level policies?

## 🔄 Account Type Analysis (Draft)

### Account Types

#### Savings Account

Purpose:

- Long-term savings.

Potential Withdrawal Rules:

- Minimum balance requirement.
- Maximum withdrawal count per month.

Potential Additional State:

- withdrawalCount
- monthlyWithdrawalLimit

#### Current Account

Purpose:

- Daily transactions.

Potential Withdrawal Rules:

- Overdraft allowed.

Potential Additional State:

- overdraftLimit

#### Business Account

Purpose:

- Commercial transactions.

Potential Withdrawal Rules:

- Higher withdrawal limits.

Potential Additional State:

- maxWithdrawalAmount

---

### Design Observation:

Savings Account, Current Account, and Business Account
appear to be specialized versions of the same concept:
Account.

They share core responsibilities and state,
but may enforce different business rules during operations
such as Withdraw and Transfer.

This is the first identified candidate for behavior variation
and future polymorphism analysis.

---

### Shared Responsibilities

All account types currently share the core Account responsibilities:

- Deposit money
- Withdraw money
- Transfer money
- Provide balance information
- Become suspended
- Become active
- Protect balance rules
- Protect account status rules

### Behavior Variation Identified

The following operations may behave differently depending on account type:

- Withdraw Money
- Transfer Money

Examples of variation:

#### Savings Account

- Minimum balance requirement
- Withdrawal count limits

#### Current Account

- Overdraft support

#### Business Account

- Higher transfer / withdrawal limits

### Design Observation

All account types represent the same domain concept: Account.

They share the same core responsibilities and lifecycle, but may enforce different business rules during Withdraw and Transfer operations.

This is the first identified candidate for future polymorphism analysis.

Same abstraction:
Account

Different behavior:
Withdraw
Transfer
