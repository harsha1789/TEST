import os
from scanners.security import run_bandit_scan
from scanners.secrets import run_trufflehog_scan
from scanners.static_analysis import run_semgrep_scan
from scanners.zap import run_zap_scan
from scanners.ssl_scan import run_ssl_scan
from scanners.performance import run_performance_check
from utils.summary import generate_summary

REPORT_DIR = "repo_guardian_bot_v2/reports"
os.makedirs(REPORT_DIR, exist_ok=True)

def main():
    print("[INFO] Running RepoGuardianBot v2 scans...")

    results = {
        "Bandit": run_bandit_scan(REPORT_DIR),
        "TruffleHog": run_trufflehog_scan(REPORT_DIR),
        "Semgrep": run_semgrep_scan(REPORT_DIR),
        "ZAP": run_zap_scan(REPORT_DIR),
        "SSL": run_ssl_scan(REPORT_DIR),
        "Performance": run_performance_check(REPORT_DIR),
    }

    print("\n[SUMMARY] Scan Results")
    print("-" * 40)
    for key, val in results.items():
        print(f"{key}: {val}")

    generate_summary(REPORT_DIR)

if __name__ == "__main__":
    main()
