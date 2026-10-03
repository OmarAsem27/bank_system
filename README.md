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

### Application

#### Responsibilities

- Show menu
- Collect user input
- Request operations from Bank
- Present results to the user

#### Role

Application is the boundary/orchestration object responsible for user interaction.

It does not own banking business rules.

---

## 🏷️ Object Properties

### Customer

- `id`
- `name`
- `status`
- `accounts`

### Account

Core Account state:

- `balance`
- `status`
- `accountType`

### Transaction

- `amount`
- `createdAt`
- `status`

### Bank

- `customers`
- `accounts`
- `transactions`

---

## 🔗 Relationship Model

### Customer

Owns:

- Nothing directly at the Bank storage/lifecycle level

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

> Note: Account-to-Transaction referencing is part of the current design and may be revisited during implementation/refactoring if it introduces unnecessary coupling.

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

## 📦 Storage Ownership Model

### Bank

Bank owns the collections that represent the system's managed banking data.

#### Customers

Bank owns Customers because:

- Bank creates Customers when needed
- Bank locates Customers
- Bank coordinates operations involving Customers
- Customer lifecycle exists within the Bank

#### Accounts

Bank owns Accounts because:

- Bank opens Accounts
- Bank locates Accounts
- Bank coordinates operations involving Accounts
- Account lifecycle exists within the Bank

#### Transactions

Bank owns Transactions because:

- Bank creates Transactions
- Bank locates Transactions
- Bank coordinates operations that generate Transactions
- Transaction lifecycle exists within the Bank

The concrete Java collection types are intentionally deferred to implementation.

---

## 📜 Business Rules Discovered So Far

### Customer

- Customer maintains its relationship with owned Accounts.
- Customer has a lifecycle.
- Customer may be Active or Blocked.

### Account

- Account owns balance-related decisions.
- Account owns withdrawal-related decisions.
- Account status affects available operations.
- Account protects its own balance rules.
- Account protects its own status rules.

### Transaction

- Transaction represents a money movement event.
- Transaction has a lifecycle.
- Transaction protects its lifecycle transitions.
- Transaction validates its creation rules.

### Bank

- Bank coordinates banking operations.
- Bank locates managed domain objects.
- Bank enforces bank-level policies.
- Bank coordinates operations but does not directly manipulate Account balance rules.

---

## 📋 Supported Use Cases

1. Open Account
2. Deposit Money
3. Withdraw Money
4. Transfer Money

These four use cases define the current scope of the project.

Foreign currency exchange and loans were discussed as possible future banking operations but are intentionally excluded from the current scope to keep the project focused on its OOD learning goals.

---

## 🤝 Collaboration Model

### Use Case 1: Open Account

**Actor:** User

**Goal:** Open a new account and associate it with a Customer.

**Objects Involved:**

- Application
- Bank
- Customer
- Account

**Flow:**

1. Application shows menu.
2. User selects Open Account.
3. Application collects:
   - Customer identifier
   - Customer name
   - Account type
4. Application requests account opening from Bank.
5. Bank locates Customer.
6. If Customer does not exist:
   - Bank creates Customer.
7. If Customer exists:
   - Bank verifies account-opening eligibility.
8. Bank creates Account.
9. Bank associates Account with Customer.
10. Application presents the result.

---

### Use Case 2: Deposit Money

**Actor:** User

**Goal:** Deposit money into an existing account.

**Objects Involved:**

- Application
- Bank
- Account
- Transaction

**Flow:**

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

### Use Case 3: Withdraw Money

**Actor:** User

**Goal:** Withdraw money from an existing account.

**Objects Involved:**

- Application
- Bank
- Account
- Transaction

**Flow:**

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
9. Account verifies the withdrawal against its balance rules.
10. If withdrawal is not allowed:
    - Application presents failure result.
11. If withdrawal is allowed:
    - Account updates its balance.
12. Bank creates Transaction.
13. Transaction validates its creation rules.
14. Transaction records the money movement event.
15. Application presents success result.

---

### Use Case 4: Transfer Money

**Actor:** User

**Goal:** Transfer money from one existing account to another existing account.

**Objects Involved:**

- Application
- Bank
- Account
- Transaction

**Flow:**

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
7. Bank requests the source Account to perform transfer.
8. Source Account verifies transfer eligibility according to its rules and status.
9. Source Account verifies the transfer against its balance rules.
10. If transfer is not allowed:
    - Application presents failure result.
11. If transfer is allowed:
    - Accounts update balances according to transfer rules.
12. Bank creates Transaction.
13. Transaction validates its creation rules.
14. Transaction records the money movement event.
15. Application presents success result.

---

## 🔄 Account Type Analysis

### Core Design Decision

The following are specialized versions of the same domain concept:

- Savings Account
- Current Account
- Business Account

They share the same core Account responsibilities and lifecycle, but some operations enforce different business rules.

This is a behavior variation point and the project's first strong candidate for polymorphism.

---

### Shared Responsibilities

All Account types share the core Account responsibilities:

- Deposit money
- Withdraw money
- Transfer money
- Provide balance information
- Become suspended
- Become active
- Protect balance rules
- Protect account status rules

---

### Behavior Variation Identified

The following operations may behave differently depending on Account type:

- Withdraw Money
- Transfer Money

Deposit currently has no meaningful behavior variation in the project scope.

---

### Savings Account

**Purpose:**

- Long-term savings

**Selected Withdrawal Rules:**

- Minimum balance requirement
- Maximum number of withdrawals per month

**Additional State Needed:**

- `withdrawalCount`
- `monthlyWithdrawalLimit`

---

### Current Account

**Purpose:**

- Daily transactions

**Selected Withdrawal Rule:**

- Overdraft support

**Additional State Needed:**

- `overdraftLimit`

---

### Business Account

**Purpose:**

- Commercial transactions

**Selected Withdrawal Rule:**

- Higher withdrawal limit

**Additional State Needed:**

- `maxWithdrawalAmount`

---

### Polymorphism Design Observation

All three specialized Account types are still Accounts.

They have:

- The same abstraction
- The same core responsibilities
- The same general lifecycle

But they may enforce different rules when performing the same responsibility.

The key design pattern discovered is:

> Same responsibility, different behavior.

This is the reason polymorphism may be appropriate.

Polymorphism is not being introduced merely because multiple Account types exist.

It is being considered because the same operation, such as withdrawal or transfer, may require different business behavior depending on the Account specialization.

---

## 🚧 Open Design Questions

These questions are intentionally deferred until implementation or refactoring reveals that they must be resolved.

### Customer

- Are additional customer-specific business rules necessary for the current scope?

### Account

- What exact numeric values should be used for minimum balance, withdrawal limits, overdraft limits, and business withdrawal limits?
- Should transfer rules vary in exactly the same way as withdrawal rules?

### Transaction

- Should failed banking operations create failed Transaction records, or should Transaction be created only after a successful money movement?
- Does Account need to directly reference Transactions, or is Bank ownership sufficient?

### Bank

- Which exact bank-level policies are required beyond the current use cases?

---

## 🎯 Project Learning Goals

Project 3 is designed to introduce and practice:

- Interfaces
- Polymorphism
- Exceptions
- Deeper Object-Oriented Design

The project intentionally avoids unnecessary banking complexity.

The goal is not to simulate a complete real-world banking system.

The banking rules exist only to create enough meaningful domain variation to practice better object-oriented design.

---

## 🚀 Implementation Entry Point

Phase 8 begins with domain objects before higher-level coordinators.

A useful dependency-oriented implementation order is:

1. Customer
2. Account abstraction
3. Savings Account
4. Current Account
5. Business Account
6. Transaction
7. Bank
8. Application

This order may be adjusted if implementation reveals a better dependency sequence.

### Why Customer First?

Customer currently has the fewest dependencies among the core domain objects.

It can be implemented and validated without requiring Bank, Transaction, or Application.

This keeps implementation aligned with the same principle used throughout the project:

> Build stable domain behavior before higher-level coordination.

---

## 📍 Current Project Status

Analysis and design for the current project scope are complete.

The project is ready to enter:

**Phase 8 — Implement**

The next implementation focus is:

**Customer**
