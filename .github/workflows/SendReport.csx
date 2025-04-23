#r "nuget: MailKit, 3.5.0"
#r "nuget: MimeKit, 3.5.0"

using System;
using System.IO;
using System.Linq;
using MimeKit;
using MailKit.Net.Smtp;
using MailKit.Security; // ✅ THIS IS REQUIRED
using System.IO.Compression;


// ✅ Fetch environment variables from GitHub Actions
string reportPath = Environment.GetEnvironmentVariable("report-path");
string screenshotsFolder = Environment.GetEnvironmentVariable("screenshots-path");

string smtpServer = Environment.GetEnvironmentVariable("SMTP_SERVER");
string smtpPortStr = Environment.GetEnvironmentVariable("SMTP_PORT");
string smtpUser = Environment.GetEnvironmentVariable("SMTP_USERNAME");
string smtpPass = Environment.GetEnvironmentVariable("SMTP_PASSWORD");

string smtpTo = "h.toshniwal@zensar.com";
string smtpFrom = smtpUser;
string subject = "Automation Test Report - GitHub Actions";
string bodyText = @"Hello Team member,

Please find the attached HTML report and screenshots.

Regards,
CI/CD Bot";

// ✅ Validation checks
if (string.IsNullOrEmpty(reportPath) || !File.Exists(reportPath))
{
    Console.WriteLine("❌ Report file not found.");
    Environment.Exit(1);
}

if (string.IsNullOrEmpty(smtpServer) || string.IsNullOrEmpty(smtpPortStr) ||
    string.IsNullOrEmpty(smtpUser) || string.IsNullOrEmpty(smtpPass))
{
    Console.WriteLine("❌ Missing SMTP configuration.");
    Environment.Exit(1);
}

int smtpPort;
if (!int.TryParse(smtpPortStr, out smtpPort))
{
    Console.WriteLine("❌ SMTP port is not a valid number.");
    Environment.Exit(1);
}

// ✅ Zip screenshots folder
string zipFile = $"{screenshotsFolder}.zip";
if (!string.IsNullOrEmpty(screenshotsFolder) && Directory.Exists(screenshotsFolder))
{
    if (File.Exists(zipFile)) File.Delete(zipFile);
    ZipFile.CreateFromDirectory(screenshotsFolder, zipFile);
    Console.WriteLine($"📦 Zipped screenshots folder: {zipFile}");
}
else
{
    Console.WriteLine($"⚠️ Screenshots folder not found at {screenshotsFolder}, skipping ZIP.");
    zipFile = null;
}

// ✅ Compose email
var message = new MimeMessage();
message.From.Add(MailboxAddress.Parse(smtpFrom));
message.To.Add(MailboxAddress.Parse(smtpTo));
message.Subject = subject;

var builder = new BodyBuilder
{
    TextBody = bodyText
};

// ✅ Attach HTML report
builder.Attachments.Add(reportPath);

// ✅ Attach ZIP if available
if (!string.IsNullOrEmpty(zipFile) && File.Exists(zipFile))
{
    builder.Attachments.Add(zipFile);
}

message.Body = builder.ToMessageBody();

// Decide the secure socket option based on port
SecureSocketOptions socketOption = smtpPort == 465
    ? SecureSocketOptions.SslOnConnect
    : SecureSocketOptions.StartTls;

// Send email using MailKit
using (var smtpClient = new SmtpClient())
{
    smtpClient.Connect(smtpServer, smtpPort, socketOption);
    smtpClient.Authenticate(smtpUser, smtpPass);
    smtpClient.Send(message);
    smtpClient.Disconnect(true);
    Console.WriteLine("✅ Email sent successfully with report and screenshots.");
}
