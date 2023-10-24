# Tuition Reimbursement Management System

## Employees:
* **As an Employee, I can login.**
* **As an Employee, I can logout.**
* As an Employee, I can claim up to 1000 per year, resetting New Years Day.
* As an Employee, I am eligible to request compensation for University Courses(80%), Seminars(60%), Cert Prep Classes(75%), Certifications(100%), Technical Training(90%), Other(30%).
* As an Employee, my current Available Reimbursement is defined as Available = Yearly Allowance - Awarded - Pending.
* As an Employee, I have the option to cancel a pending reimbursement request.
* As an Employee, I can view my pending reimbursement requests.
* As an Employee, I can view my awarded reimbursement requests.
* As an Employee, I can view my available reimbursement amount.
* As an Employee, I can view my personal information.
* As an Employee, if the reimbursement from an event would put me over my yearly allowance I will instead be reimbursed the amount that would not exceed my yearly allowance.
* As an Employee, I may not be reimbursed for course materials such as books.
* As an Employee, my reimbursement Forms are submitted to my direct Supervisor.
* As an Employee, I must attach my grade or presentation upon completion of the event.

## Supervisors:
* As a Supervisor, I can view all reimbursement forms awaiting my approval.
* As a Supervisor, when I approve a form it is sent to the Department Head.
* As a Supervisor, if I am also a Department Head, the Department Head approval step is skipped.
* As a Supervisor, if I do not approve of a form in a timely manner, it will be automatically approved.
* As a Supervisor, I may request additional information from the Employee before approving or denying a form.
* As a Supervisor, I can view all reimbursement forms I have previously approved.

## Department Heads:
* As a Department Head, I can view all reimbursement forms awaiting my approval.
* As a Department Head, when I approve a form it is sent to the Benefits Coordinator.
* As a Department Head, if I do not approve of a form in a timely manner, it will be automatically approved.
* As a Department Head, I may request additional information from either the Employee or the Supervisor before approving or denying a form.
* As a Department Head, I can view all reimbursement forms I have previously approved.
* As a Department Head, I can view all reimbursement forms I have previously denied.
* As a Department Head, I must approve any attached Presentations after the completion of the event.

## Benefits Coordinators:
* As a Benefits Coordinator, I can view all reimbursement forms awaiting my approval.
* As a Benefits Coordinator, I must always provide manual approval or denial of a form.
* As a Benefits Coordinator, if I do not approve of a form in a timely manner, an escalation email will be sent to my Supervisor.
* As a Benefits Coordinator, when I approve a form it is sent to the Employee.
* As a Benefits Coordinator, I may request additional information from the Employee, the Supervisor, or the Department Head before approving or denying a form.
* As a Benefits Coordinator, I can alter the reimbursement amount, but the Employee must be notified.
* As a Benefits Coordinator, I may approve of a Reimbursement amount beyond the Employee's Available Reimbursement. Justification must be provided and the amount must be marked as exceeding available funds for reporting purposes.
* As a Benefits Coordinator, I must confirm that any attached Grade is passing before approving a form.

## Forms:
* As a Form, I can be created.
* As a Form, I can be updated.
* As a Form, I can be deleted.
* As a Form, I can be read.
* As a Form, I can be submitted.
* As a Form, I can be approved.
* As a Form, I can be denied.
* As a Form, I must collect: employee info, date, time, location, description, cost, type, and grading format the event, as well as a work-related justification.
* As a Form, I may optionally include the amount of work time they will be missed (if any), as well as event-related attachments (.pdf, .png, .jpeg, .txt, or .doc).
* As a Form, I may contain .msg attachment(S) from the Supervisor or Department Head granting prior approval.
* As a Form, I should present the projected reimbursement amount as a read-only field.
* As a Form, I am pending until the event has completed and the grade/presentation has been approved.
* As a Form, I may not be submitted less than a week prior to the start of an event.
* As a Form, if my event is scheduled to begin less than two weeks from today, I must be marked urgent.
* As a Form, I must contain the passing grade needed for the event. If no grade is provided, a default value will be used.
* As a Form, if I contain an approval attachment from the Supervisor or Department Head, I automatically skip the relevant approval step.
* As a Form, if I am denied, a reason must be supplied.
* As a Form, I can support event with the following grading formats: Grade, Presentation required, Pass/Fail, or Other.