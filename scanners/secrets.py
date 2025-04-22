import subprocess

def run_trufflehog_scan():
    try:
        subprocess.run(["trufflehog", "filesystem", ".", "--json", "--output", "repo_guardian_bot/reports/trufflehog.json"], check=True)
        return "Secrets scan saved to reports/trufflehog.json"
    except Exception as e:
        return f"TruffleHog scan failed: {str(e)}"
