def run_zap_scan(report_dir):
    path = f"{report_dir}/zap_report.txt"
    with open(path, "w") as f:
        f.write("ZAP scan simulated. Use OWASP ZAP CLI for real scans.")
    return f"ZAP scan saved to {path}"
