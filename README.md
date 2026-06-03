# 🎓 EduSphere | Student Result Management System

EduSphere is a robust, production-ready enterprise web application designed to streamline student registration, academic record tracking, and dynamic term-end performance evaluations. Built using core enterprise Java technologies, it features a dual-portal interface tailored specifically for Institutional Administrators and Students.

---

## 🚀 Key System Features

### 🛠️ Administrative Control Center
Live Student Registry: Seamless onboarding of students into the relational schema with unique academic credentials.
Instant Academic Publishing:** Seamless term-end marks publication mapped directly through composite foreign keys.
Database Synchronizer:** Real-time dashboard view fetching active engine records through standard servlet integrations.

### 🔑 Student Self-Service Gateway
Secure Unified Sign-In: A premium, interactive multi-role card layout enabling secure routing based on access levels.
Dynamic Report Card Engine: Automatically compiles aggregate scores, real-time percentages, and university-grade compliance classifications (Grades A+ through F).

---

## 🏗️ Technical Architecture & Stack

The application is engineered on top of a multi-tiered structural flow that ensures clean decoupling of presentation layers, request handling, and backend logic.

Backend Engine: Java Enterprise Edition (JEE) Servlets, Session Tracking API
Presentation Tier: Dynamic JavaServer Pages (JSP), Embedded Scriptlets, Vanilla CSS3 (Modern Responsive Layouts)
Data Layer: MySQL 8.0 Relational Engine, Java Database Connectivity (JDBC)
Build Architecture: Apache Maven 3.x
Deployment Web Container: Apache Tomcat Server

---

## 📊 System Database Schema Design

The relational database architecture relies on a highly consistent structured layout to manage entity mappings:

* **`users` Table:** Manages access credentials and system access definitions (`STUDENT` / `ADMIN`).
* **`students` Table:** Holds demographic data, academic roll numbers, and charts a strict `FOREIGN KEY` association to the user identity.
* **`subjects` Table:** Registers specific technical curriculum modules dynamically.
* **`marks` Table:** Stores raw academic metrics. Implements a composite `PRIMARY KEY (student_id, subject_id)` structure along with cascade protection rules to enforce referential integrity and entirely prevent data duplication.

---

## 📸 Project Glimpses & Execution Proof

You can review the interactive workflow, database grid state, and production screens directly inside the root repository directory:

📂 **Check out the screenshots here:** [`/screenshots`](./screenshots)

* Includes live snapshots of the revamped modern login portal layout, backend console logs, and dynamic percentage generation modules!

---

## 💻 Local Workspace Set Up

### Prerequisites
* Java Development Kit (JDK 11 or higher)
* Apache Maven Installed
* MySQL Server instances running locally

### Installation & Execution Flow

1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/YOUR_USERNAME/EduSphere-Portal.git](https://github.com/YOUR_USERNAME/EduSphere-Portal.git)
   cd EduSphere-Portal
